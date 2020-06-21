import React from "react";
import './job-execution-list.scss'
import {
    Column,
    DataGrid,
    Editing,
    FilterRow,
    Form,
    HeaderFilter,
    MasterDetail,
    Pager,
    Paging,
    RemoteOperations,
    Selection,
    Sorting
} from "devextreme-react/data-grid";
import {dateTimeCellTemplate, getFormattedTime, getRunningTime} from "../../utils/date-time-util";
import {JobExecutionDataSource} from "./job-execution-data-source";
import UpdateTimer, {updateIntervals} from "../../utils/update-timer";
import {StepExecutionListPage} from "../index";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {GroupItem, SimpleItem} from "devextreme-react/form";
import JobExecutionParamList from "./job-execution-param-list";
import JobExecutionLogList from "./job-execution-log-list";
import {getItem} from "../../utils/server-connection";

class JobExecutionList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: {},
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.restartJob = this.restartJob.bind(this);
        this.intervals = [
            {interval: 0, text: 'None'},
            {interval: 30000, text: '30 sec'},
            {interval: 60000, text: '1 min'},
            {interval: 300000, text: '5 min'},
            {interval: 600000, text: '10 min'},
            {interval: 1800000, text: '30 min'},
            {interval: 3600000, text: '1 hour'}];
        this.intervalSelectOptions = {
            width: 140,
            items: updateIntervals,
            valueExpr: "interval",
            displayExpr: "text",
            placeholder: "Update interval",
            value: this.intervals[0].id,
            onValueChanged: (args) => {
                clearTimeout(this.state.timer);
                if (args.value > 0) {
                    this.state.timer = setInterval(() => this.setState({}), args.value);
                    this.render();
                }
            }
        }
    }

    selectionChanged(e) {
        this.setState({currentJobExecution: e.data});
    }

    restartJob(e) {
        let jobExecution = e.row.data;
        getItem(jobExecution._links.restart.href)
            .then((data) => {
                this.setState({currentJobExecution: data});
            });
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Executions</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }, hint: 'Refresh job execution list.'
                                }}/>
                            <Item
                                location="after"
                                widget="dxSelectBox"
                                locateInMenu="auto"
                                options={this.intervalSelectOptions}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={JobExecutionDataSource(this)}
                            hoverStateEnabled={true}
                            allowColumnReordering={true}
                            allowColumnResizing={true}
                            columnResizingMode={'widget'}
                            columnMinWidth={50}
                            columnAutoWidth={true}
                            showColumnLines={true}
                            showRowLines={true}
                            showBorders={true}
                            rowAlternationEnabled={true}
                            onEditingStart={this.selectionChanged}>
                            <Selection mode={'single'}/>
                            <FilterRow visible={true} applyFilter={'auto'}/>
                            <HeaderFilter visible={true}/>
                            <Sorting mode={'multiple'}/>
                            <Editing
                                mode={'form'}
                                useIcons={true}
                                allowDeleting={true}
                                allowUpdating={true}>
                                <Form>
                                    <GroupItem colCount={2} caption={"Job Execution Details: " + this.state.currentJobExecution.jobName}>
                                        <SimpleItem dataField="jobName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="hostName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="nodeName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="status" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="jobPid" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="jobGroup" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="jobKey" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="id" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="jobExecutionId" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="jobVersion" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                                    </GroupItem>
                                    <GroupItem colCount={2} caption={"Timing"}>
                                        <SimpleItem dataField="startTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'startTime'), readOnly: true}}/>
                                        <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'endTime'), readOnly: true}}/>
                                        <SimpleItem dataField="createTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'createTime'), readOnly: true}}/>
                                        <SimpleItem dataField="lastUpdated" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'lastUpdated'), readOnly: true}}/>
                                        <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getRunningTime(this.state.currentJobExecution)}}/>
                                        <SimpleItem dataField="startedBy" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.state.currentJobExecution.startedBy}}/>
                                        <SimpleItem dataField="exitCode" readOnly={true}/>
                                        <SimpleItem dataField="exitMessage" readOnly={true}/>
                                    </GroupItem>
                                    <GroupItem colSpan={2} caption={"Parameters"}>
                                        <JobExecutionParamList jobExecution={this.state.currentJobExecution}/>
                                    </GroupItem>
                                    <GroupItem colSpan={2} caption={"Logs"}>
                                        <JobExecutionLogList jobExecution={this.state.currentJobExecution}/>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'modifiedAt'), readOnly: true}}/>
                                    </GroupItem>
                                </Form>
                            </Editing>
                            <Column
                                caption={'ID'}
                                dataField={'id'}
                                visible={false}/>
                            <Column
                                caption={'Job Name'}
                                dataField={'jobName'}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}
                                allowEditing={true}>
                                <HeaderFilter allowSearch={true}/>
                            </Column>
                            <Column
                                dataField={'jobGroup'}
                                visible={false}/>
                            <Column
                                dataField={'jobKey'}
                                visible={false}/>
                            <Column
                                caption={'Host name'}
                                dataField={'hostName'}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}>
                                <HeaderFilter allowSearch={true}/>
                            </Column>
                            <Column
                                caption={'Node name'}
                                dataField={'nodeName'}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}>
                                <HeaderFilter allowSearch={true}/>
                            </Column>
                            <Column
                                dataField={'status'}
                                caption={'Status'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}
                                width={100}/>
                            <Column
                                dataField={'jobExecutionId'}
                                caption={'ID'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}
                                width={50}/>
                            <Column
                                dataField={'jobPid'}
                                caption={'PID'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}
                                width={50}
                                visible={false}/>
                            <Column
                                dataField={'jobVersion'}
                                caption={'Version'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={60}/>
                            <Column
                                dataField={'exitCode'}
                                caption={'Exit Code'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'exitMessage'}
                                caption={'Exit Message'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'createTime'}
                                caption={'Created'}
                                dataType={'datetime'}
                                cellTemplate={dateTimeCellTemplate}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={120}
                                visible={false}/>
                            <Column
                                dataField={'startTime'}
                                caption={'Started'}
                                dataType={'datetime'}
                                cellTemplate={dateTimeCellTemplate}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                dataField={'endTime'}
                                caption={'Ended'}
                                dataType={'datetime'}
                                cellTemplate={dateTimeCellTemplate}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                dataField={'lastUpdated'}
                                caption={'Last Update'}
                                dataType={'datetime'}
                                cellTemplate={dateTimeCellTemplate}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                calculateCellValue={getRunningTime}
                                dataField={'runningTime'}
                                caption={'Running Time'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={100}/>
                            <Column
                                dataField={'startedBy'}
                                visible={false}/>
                            <Column
                                dataField={'createdBy'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'createdAt'}
                                dataType={'datetime'}
                                visible={false}/>
                            <Column
                                dataField={'modifiedBy'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'modifiedAt'}
                                dataType={'datetime'}
                                visible={false}/>
                            <RemoteOperations sorting={true} paging={true}/>
                            <Paging defaultPageSize={10}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                            <MasterDetail enabled={true} component={StepExecutionListPage}/>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={80}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit job execution entry.',
                                        icon: 'material-icons-outlined ic-edit',
                                    },
                                    {
                                        name: 'restart',
                                        hint: 'Restart the job execution entry.',
                                        icon: 'material-icons-outlined ic-restart',
                                        onClick: this.restartJob
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job execution entry.',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobExecutionList;

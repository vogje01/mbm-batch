import React from "react";
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
import {JobExecutionDataSource} from "./job-execution-data-source";
import UpdateTimer from "../../utils/update-timer";
import './job-execution-list.scss'
import {getCreateTime, getEndTime, getLastUpdatedTime, getRunningTime, getStartTime} from "../../utils/date-time-util";
import {StepExecutionListPage} from "../index";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {GroupItem, SimpleItem} from "devextreme-react/form";

class JobExecutionList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: {},
        };
        this.selectionChanged = this.selectionChanged.bind(this);
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
            items: this.intervals,
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
                            id={'jobTable'}
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
                                        <SimpleItem dataField="jobName" readOnly={true}/>
                                        <SimpleItem dataField="hostName" readOnly={true}/>
                                        <SimpleItem dataField="nodeName" readOnly={true}/>
                                        <SimpleItem dataField="status" readOnly={true}/>
                                        <SimpleItem dataField="jobPid" readOnly={true}/>
                                        <SimpleItem dataField="id" readOnly={true}/>
                                        <SimpleItem dataField="jobVersion" readOnly={true}/>
                                    </GroupItem>
                                    <GroupItem colCount={2} caption={"Timing"}>
                                        <SimpleItem dataField="startTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getStartTime(this.state.currentJobExecution), readOnly: true}}/>
                                        <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getEndTime(this.state.currentJobExecution), readOnly: true}}/>
                                        <SimpleItem dataField="createTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getCreateTime(this.state.currentJobExecution), readOnly: true}}/>
                                        <SimpleItem dataField="lastUpdated" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getLastUpdatedTime(this.state.currentJobExecution), readOnly: true}}/>
                                        <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getRunningTime(this.state.currentJobExecution)}}/>
                                    </GroupItem>
                                    <GroupItem colSpan={2} caption={"Logs"}>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                                    </GroupItem>
                                </Form>
                            </Editing>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={80}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit job execution entry',
                                        icon: 'material-icons-outlined ic-edit md-18',
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job execution entry',
                                        icon: 'material-icons-outlined ic-delete md-18'
                                    }
                                ]}/>
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
                                width={50}
                                visible={false}/>
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
                                calculateCellValue={getCreateTime}
                                dataField={'createTime'}
                                caption={'Created'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={120}
                                visible={false}/>
                            <Column
                                calculateCellValue={getStartTime}
                                dataField={'startTime'}
                                caption={'Started'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                calculateCellValue={getEndTime}
                                dataField={'endTime'}
                                caption={'Ended'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                calculateCellValue={getLastUpdatedTime}
                                dataField={'lastUpdated'}
                                caption={'Last Update'}
                                dataType={'datetime'}
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
                            <RemoteOperations sorting={true} paging={true}/>
                            <Paging defaultPageSize={10}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                            <MasterDetail enabled={true} component={StepExecutionListPage}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobExecutionList;

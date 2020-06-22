import React from "react";
import './job-execution-log-list.scss'
import {Column, ColumnChooser, DataGrid, FilterRow, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {getTimestamp} from "../../utils/date-time-util";
import UpdateTimer, {updateIntervals} from "../../utils/update-timer";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {AgentDataSource} from "../agent/agent-data-source";
import {JobExecutionLogDataSource} from "./job-execution-log-data-source";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";

class LogList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: {},
            isThreadVisible: false,
            isHostVisible: false,
            isNodeVisible: false
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.optionChanged = this.optionChanged.bind(this);
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
        this.levels = [
            {name: 'TRACE', value: 'TRACE'},
            {name: 'DEBUG', value: 'DEBUG'},
            {name: 'INFO', value: 'INFO'},
            {name: 'WARNING', value: 'WARN'},
            {name: 'ERROR', value: 'ERROR'}
        ];
    }

    selectionChanged(e) {
        this.setState({currentJobExecution: e.data});
    }

    optionChanged(e) {
        if (e.fullName === 'columns[0].visible') {
            this.setState({
                isThreadVisible: e.value
            });
        }
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Execution Logs</h2>
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
                                location="before"
                                widget="dxSelectBox"
                                options={{
                                    dataSource: AgentDataSource(),
                                    keyExpr: 'id',
                                    displayExpr: 'hostName',
                                    hint: 'Select host.',
                                    placeholder: 'Select host...'
                                }}/>
                            <Item
                                location="before"
                                widget="dxSelectBox"
                                options={{
                                    dataSource: AgentDataSource(),
                                    keyExpr: 'id',
                                    displayExpr: 'nodeName',
                                    hint: 'Select node.',
                                    placeholder: 'Select node...'
                                }}/>
                            <Item
                                location="before"
                                widget="dxSelectBox"
                                options={{
                                    dataSource: this.levels,
                                    keyExpr: 'value',
                                    displayExpr: 'name',
                                    hint: 'Select level.',
                                    placeholder: 'Select level...'
                                }}/>
                            <Item
                                location="before"
                                widget="dxSelectBox"
                                options={{
                                    dataSource: JobDefinitionDataSource(),
                                    keyExpr: 'id',
                                    displayExpr: 'name',
                                    hint: 'Select job.',
                                    placeholder: 'Select job...',
                                    cssClass: 'wide-select'
                                }}/>
                            <Item
                                location="after"
                                widget="dxSelectBox"
                                locateInMenu="auto"
                                options={this.intervalSelectOptions}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={JobExecutionLogDataSource()}
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
                            onOptionChanged={this.optionChanged}>
                            <Selection mode={'single'}/>
                            <FilterRow visible={true} applyFilter={'auto'}/>
                            <HeaderFilter visible={true}/>
                            <Sorting mode={'multiple'}/>
                            <ColumnChooser
                                enabled={true}
                                mode="select"/>
                            {/*<Editing
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
                                        <SimpleItem dataField="jobExecutionId" readOnly={true}/>
                                        <SimpleItem dataField="jobVersion" readOnly={true}/>
                                        <SimpleItem dataField="exitCode" readOnly={true}/>
                                        <SimpleItem dataField="exitMessage" readOnly={true}/>
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
                            </Editing>*/}
                            <Column
                                caption={'Timestamp'}
                                dataField={'timestamp'}
                                calculateCellValue={getTimestamp}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                caption={'Level'}
                                dataField={'level'}
                                dataType={'string'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}/>
                            <Column
                                caption={'Hostname'}
                                dataField={'hostName'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}
                                visible={this.state.isHostVisible}/>
                            <Column
                                caption={'Node name'}
                                dataField={'nodeName'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}
                                visible={this.state.isNodeVisible}/>
                            <Column
                                caption={'Logger'}
                                dataField={'loggerName'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}>
                                <HeaderFilter allowSearch={true}/>
                            </Column>
                            <Column caption={'Job'}
                                    showInColumnChooser={true}>
                                <Column
                                    caption={'Name'}
                                    dataField={'jobName'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}>
                                    <HeaderFilter allowSearch={true}/>
                                </Column>
                                <Column
                                    caption={'PID'}
                                    dataField={'jobPid'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}/>
                                <Column
                                    caption={'Version'}
                                    dataField={'jobVersion'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}/>
                            </Column>
                            <Column
                                caption={'Step Name'}
                                dataField={'stepName'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}>
                                <HeaderFilter allowSearch={true}/>
                            </Column>
                            <Column caption={'Thread'}
                                    visible={this.state.isThreadVisible}
                                    showInColumnChooser={true}>
                                <Column
                                    caption={'Name'}
                                    dataField={'thread'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}/>
                                <Column
                                    caption={'ID'}
                                    dataField={'threadId'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}
                                    width={50}/>
                                <Column
                                    caption={'Prio'}
                                    dataField={'threadPriority'}
                                    allowSorting={true}
                                    allowFiltering={true}
                                    allowGrouping={false}
                                    allowReordering={true}
                                    width={50}/>
                            </Column>
                            <Column
                                caption={'Message'}
                                dataField={'message'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}/>
                            <RemoteOperations sorting={true} paging={true}/>
                            <Paging defaultPageSize={10}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                            {/*<MasterDetail enabled={true} component={StepExecutionListPage}/>*/}
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default LogList;

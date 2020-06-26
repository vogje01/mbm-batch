import React from "react";
import {Column, DataGrid, Editing, FilterRow, Form, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {StepExecutionDataSource} from "./step-execution-data-source";
import UpdateTimer from "../../utils/update-timer";
import './step-execution-list.scss'
import {dateTimeCellTemplate, getFormattedTime, getRunningTime} from "../../utils/date-time-util";
import {getPctCounter} from "../../utils/counter-util";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {GroupItem, SimpleItem} from "devextreme-react/form";
import StepExecutionLogList from "./step-execution-log-list";
import SelectBox from "devextreme-react/select-box";
import {AgentDataSource} from "../agent/agent-data-source";
import Button from "devextreme-react/button";
import {addFilter, clearFilter, dropFilter} from "../../utils/filter-util";

class StepExecutionList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            filterName: 'StepExecutionInfo',
            currentStepExecution: {},
            selectedStatus: undefined,
            selectedHost: undefined,
            selectedNode: undefined,
            selectedStepName: undefined
        }
        this.selectionChanged = this.selectionChanged.bind(this);
        this.onAddStatusFilter = this.onAddStatusFilter.bind(this);
        this.onAddHostNameFilter = this.onAddHostNameFilter.bind(this);
        this.onAddNodeNameFilter = this.onAddNodeNameFilter.bind(this);
        this.onAddStepNameFilter = this.onAddStepNameFilter.bind(this);
        this.onClearFilter = this.onClearFilter.bind(this);
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
        this.statuses = [
            {key: 'Completed', value: 'COMPLETED'},
            {key: 'Started', value: 'STARTED'},
            {key: 'Starting', value: 'STARTING'},
            {key: 'Stopped', value: 'STOPPED'},
            {key: 'Stopping', value: 'STOPPING'},
            {key: 'Failed', value: 'FAILED'},
            {key: 'Abandoned', value: 'ABANDONED'},
            {key: 'Unknown', value: 'UNKNOWN'}
        ];
    }

    componentDidMount() {
        if (this.props.match && this.props.match.params) {
            this.onAddStatusFilter({value: this.props.match.params.status})
        }
    }

    selectionChanged(e) {
        this.setState({currentStepExecution: e.data});
    }

    getReadCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.readCount) : null;
    }

    getReadSkipCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.readSkipCount) : null;
    }

    getWriteCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.writeCount) : null;
    }

    getWriteSkipCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.writeSkipCount) : null;
    }

    getFilterCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.filterCount) : null;
    }

    onAddStatusFilter(e) {
        if (e.value) {
            addFilter(this.state.filterName, 'status', e.value);
            this.setState({selectedStatus: e.value});
        } else {
            dropFilter(this.state.filterName, 'status');
            this.setState({selectedStatus: null});
        }
    }

    onAddHostNameFilter(e) {
        if (e.value) {
            addFilter(this.state.filterName, 'hostName', e.value);
            this.setState({selectedHost: e.value});
        } else {
            dropFilter(this.state.filterName, 'hostName');
            this.setState({selectedHost: null});
        }
    }

    onAddNodeNameFilter(e) {
        if (e.value) {
            addFilter(this.state.filterName, 'nodeName', e.value);
            this.setState({selectedNode: e.value});
        } else {
            dropFilter(this.state.filterName, 'nodeName');
            this.setState({selectedNode: null});
        }
    }

    onAddStepNameFilter(e) {
        if (e.value) {
            addFilter(this.state.filterName, 'jobName', e.value);
            this.setState({selectedStepName: e.value});
        } else {
            dropFilter(this.state.filterName, 'jobName');
            this.setState({selectedStepName: null});
        }
    }

    onClearFilter() {
        clearFilter(this.state.filterName);
        this.setState({selectedStatus: undefined, selectedNode: undefined, selectedStepName: undefined});
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'} hidden={this.props.currentJobExecution !== undefined}>Step Executions</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'} hidden={this.props.currentJobExecution !== undefined}>
                        <Toolbar>
                            <Item location="before">
                                <SelectBox items={this.statuses} displayExpr='key' valueExpr='value' showClearButton={true}
                                           value={this.state.selectedStatus} onValueChanged={this.onAddStatusFilter}
                                           placeholder={'Select status...'} hint={'Filter job executions by status.'}/>
                            </Item>
                            <Item location="before">
                                <SelectBox dataSource={AgentDataSource()} displayExpr='nodeName' valueExpr='nodeName' showClearButton={true}
                                           value={this.state.selectedNode} onValueChanged={this.onAddNodeNameFilter}
                                           placeholder={'Select node...'} hint={'Filter job executions by node.'}/>
                            </Item>
                            {/*<Item location="before">
                                <SelectBox dataSource={JobDefinitionDataSource()} displayExpr={'name'} valueExpr={'name'} showClearButton={true}
                                           value={this.state.selectedStepName} onValueChanged={this.onAddStepNameFilter}
                                           placeholder={'Select job name...'} hint={'Filter job execution logs by job name.'}/>
                            </Item>*/}
                            <Item location="before">
                                <Button text={'Clear Filter'} onClick={this.onClearFilter.bind(this)} hint={'Clear all filter settings.'}/>
                            </Item>
                        </Toolbar>
                    </div>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-back", onClick: () => {
                                        this.props.history.goBack()
                                    }, hint: 'Go back to previous page.'
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }, hint: 'Refresh step execution list.'
                                }}/>
                            <Item
                                location="after"
                                widget="dxSelectBox"
                                locateInMenu="auto"
                                options={this.intervalSelectOptions}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={StepExecutionDataSource(this.props.data !== undefined ? this.props.data.data : undefined, this.state.filterName)}
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
                            <FilterRow visible={true}/>
                            <Selection mode={'single'}/>
                            <FilterRow visible={true} applyFilter={'auto'}/>
                            <HeaderFilter visible={true}/>
                            <Sorting mode={'multiple'}/>
                            <Editing
                                mode={'form'}
                                useIcons={true}
                                allowUpdating={true}
                                allowDeleting={true}>
                                <Form>
                                    <GroupItem colCount={2} caption={"Step Execution Details: " + this.state.currentStepExecution.stepName}>
                                        <SimpleItem dataField="jobName" readOnly={true}/>
                                        <SimpleItem dataField="stepName" readOnly={true}/>
                                        <SimpleItem dataField="hostName" readOnly={true}/>
                                        <SimpleItem dataField="nodeName" readOnly={true}/>
                                        <SimpleItem dataField="status" readOnly={true}/>
                                        <SimpleItem dataField="stepExecutionId" readOnly={true}/>
                                        <SimpleItem dataField="exitCode" readOnly={true}/>
                                        <SimpleItem dataField="exitMessage" readOnly={true}/>
                                    </GroupItem>
                                    <GroupItem colCount={2} caption={"Timing"}>
                                        <SimpleItem dataField="startTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'startTime')}}/>
                                        <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'endTime')}}/>
                                        <SimpleItem dataField="lastUpdated" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'lastUpdated')}}/>
                                        <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getRunningTime(this.state.currentStepExecution)}}/>
                                    </GroupItem>
                                    <GroupItem colSpan={2} colCount={4} caption={"Counter"}>
                                        <SimpleItem dataField="totalCount" readOnly={true}/>
                                        <SimpleItem dataField="readCount" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.getReadCount(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="readSkipCount" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.getReadSkipCount(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="writeCount" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.getWriteCount(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="writeSkipCount" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.getWriteSkipCount(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="filtered" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: this.getFilterCount(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="commitCount" readOnly={true}/>
                                        <SimpleItem dataField="rollbackCount" readOnly={true}/>
                                    </GroupItem>
                                    <GroupItem colSpan={2} caption={"Logs"}>
                                        <StepExecutionLogList stepExecution={this.state.currentStepExecution}/>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'modifiedAt'), readOnly: true}}/>
                                    </GroupItem>
                                </Form>
                            </Editing>
                            <Column
                                caption={'Job Name'}
                                dataField={'jobName'}
                                allowSorting={true}
                                allowFiltering={true}
                                allowGrouping={false}
                                allowReordering={true}>
                                <HeaderFilter allowSearch={true}/>
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
                            <Column
                                dataField={'stepExecutionId'}
                                caption={'ID'}
                                allowSorting={true}
                                allowGrouping={false}
                                allowReordering={true}
                                width={30}/>
                            <Column
                                dataField={'status'}
                                caption={'Status'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}
                                width={100}/>
                            <Column
                                dataField={'exitCode'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'exitMessage'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'hostName'}
                                caption={'Host name'}
                                dataType={'string'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}/>
                            <Column
                                dataField={'nodeName'}
                                caption={'Node name'}
                                dataType={'string'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={true}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                dataField={'startTime'}
                                caption={'Started'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                dataField={'endTime'}
                                caption={'Ended'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                dataField={'lastUpdated'}
                                caption={'Last Update'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                calculateCellValue={getRunningTime}
                                dataField={'runningTime'}
                                caption={'Running'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={100}/>
                            <Column
                                dataField={'totalCount'}
                                caption={'Total'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={60}/>
                            <Column
                                calculateCellValue={this.getReadCount}
                                dataField={'readCount'}
                                caption={'Read'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={100}/>
                            <Column
                                calculateCellValue={this.getReadSkipCount}
                                dataField={'readSkipCount'}
                                caption={'Read Skip'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                visible={false}
                                width={100}/>
                            <Column
                                calculateCellValue={this.getWriteCount}
                                caption={'Write'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={100}/>
                            <Column
                                calculateCellValue={this.getWriteSkipCount}
                                caption={'Write Skip'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                visible={false}
                                width={100}/>
                            <Column
                                calculateCellValue={this.getFilterCount}
                                caption={'Filtered'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={100}/>
                            <Column
                                dataField={'commitCount'}
                                caption={'Commits'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                visible={false}
                                width={60}/>
                            <Column
                                dataField={'rollbackCount'}
                                caption={'Rollbacks'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                visible={false}
                                width={60}/>
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
                            <RemoteOperations sorting={true} paging={true}/>
                            <Paging defaultPageSize={10}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default StepExecutionList;
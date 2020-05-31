import React from "react";
import {Column, DataGrid, Editing, FilterRow, Form, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {StepExecutionDataSource} from "./step-execution-data-source";
import UpdateTimer from "../../utils/update-timer";
import './step-execution-list.scss'
import {getCreateTime, getEndTime, getLastUpdatedTime, getRunningTime, getStartTime} from "../../utils/date-time-util";
import {getPctCounter} from "../../utils/counter-util";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {GroupItem, SimpleItem} from "devextreme-react/form";

class StepExecutionList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: {}
        }
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

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Step Executions</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
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
                            dataSource={StepExecutionDataSource(this.props.data !== undefined ? this.props.data.data : undefined)}
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
                                                    editorOptions={{value: getStartTime(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getEndTime(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="createTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getCreateTime(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="lastUpdated" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getLastUpdatedTime(this.state.currentStepExecution)}}/>
                                        <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                                    editorOptions={{value: getRunningTime(this.state.currentStepExecution)}}/>
                                    </GroupItem>
                                    <GroupItem colCount={2} caption={"Counter"}>
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
                                    <GroupItem colSpan={4} caption={"Logs"}>
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
                                calculateCellValue={getStartTime}
                                dataField={'startTime'}
                                caption={'Started'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                calculateCellValue={getEndTime}
                                dataField={'endTime'}
                                caption={'Ended'}
                                dataType={'datetime'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                calculateCellValue={getLastUpdatedTime}
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
                                width={80}/>
                            <Column
                                calculateCellValue={this.getReadCount}
                                dataField={'readCount'}
                                caption={'Read'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getReadSkipCount}
                                dataField={'readSkipCount'}
                                caption={'Read Skip'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getWriteCount}
                                caption={'Write'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getWriteSkipCount}
                                caption={'Write Skip'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getFilterCount}
                                caption={'Filtered'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                dataField={'commitCount'}
                                caption={'Commits'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={80}/>
                            <Column
                                dataField={'rollbackCount'}
                                caption={'Rollbacks'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={80}/>
                            <RemoteOperations
                                sorting={true}
                                paging={true}/>
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
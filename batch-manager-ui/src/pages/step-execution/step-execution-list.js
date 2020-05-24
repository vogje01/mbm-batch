import React from "react";
import {Column, DataGrid, Editing, FilterRow, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {StepExecutionDataSource} from "./step-execution-data-source";
import UpdateTimer from "../../utils/update-timer";
import './step-execution-list.scss'
import {getRunningTime} from "../../utils/date-time-util";
import {getPctCounter} from "../../utils/counter-util";
import {Item, Toolbar} from "devextreme-react/toolbar";

class StepExecutionList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: {}
        }
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
        this.state.setState({currentStepExecution: e.data});
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
                            rowAlternationEnabled={true}>
                            <FilterRow visible={true}/>
                            <Selection mode={'single'}/>
                            <FilterRow visible={true} applyFilter={'auto'}/>
                            <HeaderFilter visible={true}/>
                            <Sorting mode={'multiple'}/>
                            <Editing
                                mode={'form'}
                                startEditAction={ondblclick}
                                useIcons={true}
                                allowUpdating={true}
                                allowDeleting={true}/>
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
                                width={90}/>
                            <Column
                                dataField={'startTime'}
                                caption={'Started'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}/>
                            <Column
                                dataField={'endTime'}
                                caption={'Ended'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}/>
                            <Column
                                dataField={'lastUpdated'}
                                caption={'Last Update'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}/>
                            <Column
                                calculateCellValue={getRunningTime}
                                dataField={'runningTime'}
                                caption={'Running Time'}
                                dataType={'datetime'}
                                allowSorting={true}
                                allowReordering={true}
                                width={110}/>
                            <Column
                                dataField={'totalCount'}
                                caption={'Total Count'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getReadCount}
                                caption={'Read Count'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getReadSkipCount}
                                caption={'Read Skip Count'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getWriteCount}
                                caption={'Write Count'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getWriteSkipCount}
                                caption={'Write Skip Count'}
                                dataType={'number'}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={110}/>
                            <Column
                                calculateCellValue={this.getFilterCount}
                                caption={'Filter Count'}
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
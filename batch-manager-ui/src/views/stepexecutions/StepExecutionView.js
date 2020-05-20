import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from 'devextreme-react/data-grid';

import {getRunningTime,} from '../../util/DateTimeUtil';
import {getPctCounter} from '../../util/CounterUtil';
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import StepExecutionDetails from "./StepExecutionDetails";
import {stepExecutionDataSource} from "./StepExecutionDataSource";

class StepExecutionView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecutionInfo: props.data,
            currentStepExecutionInfo: {},
            showDetails: false,
            pageSize: 20
        };
        this.toggleDetails = this.toggleDetails.bind(this);
        this.handleClickOpenDetails = this.handleClickOpenDetails.bind(this);
        this.tabs = [
            {id: 0, text: 'Main', icon: 'material-icons-outlined ic-home md-18', content: 'Main'},
            {id: 1, text: 'Data', icon: 'material-icons-outlined ic-list md-18', content: 'Data content'},
            {id: 2, text: 'Error', icon: 'material-icons-outlined ic-error md-18', content: 'Error content'},
            {id: 3, text: 'Logs', icon: 'material-icons-outlined ic-list md-18', content: 'Error content'}
        ];
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentStepExecution: e.data
        });
    }

    handleClickOpenDetails(row) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentStepExecution: row.row.data
        });
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

    deleteStep(stepExecutionInfo) {
    }

    render() {
        return (
            <React.Fragment>
                <div className="long-title"><h3>Step Execution List</h3></div>
                <DataGrid
                    dataSource={stepExecutionDataSource(this.props.data !== undefined ? this.props.data.data : undefined)}
                    hoverStateEnabled={true}
                    onRowDblClick={this.toggleDetails}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}
                    onContextMenuPreparing={(e) => {
                        if (e.row.rowType === "data") {
                            e.items = [
                                {
                                    text: 'Step Details',
                                    row: e.row.data,
                                    parent: this,
                                    onItemClick: function () {
                                        this.parent.showStepInfo(this.row);
                                    }
                                },
                                {
                                    template: '<hr>'
                                },
                                {
                                    text: 'Pause Step'
                                },
                                {
                                    text: 'Stop Step'
                                },
                                {
                                    text: 'Kill Step'
                                },
                                {
                                    template: '<hr>'
                                },
                                {
                                    text: 'Delete Step',
                                    row: e.row.data,
                                    parent: this,
                                    onItemClick: function () {
                                        this.parent.deleteStep(this.row);
                                    }
                                }
                            ]
                        }
                    }}>
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
                                onClick: this.handleClickOpenDetails
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
                    <Paging defaultPageSize={5}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                           showNavigationButtons={true} showInfo={true} visible={true}/>
                </DataGrid>
                <UpdateTimer/>
                {
                    this.state.showDetails ?
                        <StepExecutionDetails currentStepExecution={this.state.currentStepExecution} closePopup={this.toggleDetails.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }
}

export default StepExecutionView;
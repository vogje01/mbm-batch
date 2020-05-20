import React from 'react';
import {withRouter} from 'react-router-dom'

import 'devextreme/data/odata/store';
import DataGrid, {
    Column,
    Editing,
    FilterRow,
    HeaderFilter,
    MasterDetail,
    Pager,
    Paging,
    RemoteOperations,
    Selection,
    Sorting
} from 'devextreme-react/data-grid';

import StepExecutionView from '../stepexecutions/StepExecutionView';
import {getRunningTime,} from '../../util/DateTimeUtil';
import {errorMessage, infoMessage} from "../../util/MessageUtil";
import UpdateTimer from "../../components/UpdateTimer";
import JobExecutionDetails from "./JobExecutionDetails";
import FisPage from "../../components/FisPage";
import {jobExecutionDataSource} from "./JobExecutionDataSource";

class JobExecutionView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: {},
            showDetails: false,
            pageSize: 20
        };
        this.dataGrid = null;
        this.toggleDetails = this.toggleDetails.bind(this);
        this.handleClickOpenDetails = this.handleClickOpenDetails.bind(this);
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentJobExecution: e.data
        });
    }

    handleClickOpenDetails(row) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentJobExecution: row.row.data
        });
    }

    shouldComponentUpdate(nextProps, nextStatenext, nextContext) {
        jobExecutionDataSource().reload();
        return true;
    }

    clearFilter() {
        this.dataGrid.instance.clearFilter();
    }

    deleteJob(currentJobExecutionInfo) {
        if (!currentJobExecutionInfo) {
            return;
        }
        fetch(currentJobExecutionInfo._links.delete.href, {method: 'DELETE'})
            .then(() => {
                jobExecutionDataSource().reload();
                infoMessage('Job execution info has been deleted');
            })
            .catch((error) => {
                errorMessage('Could not delete job execution, error: ' + error.message);
            });
    }

    startJob(currentJobExecutionInfo) {
        if (!currentJobExecutionInfo) {
            return;
        }
        fetch(currentJobExecutionInfo._links.start.href)
            .then(() => {
                jobExecutionDataSource().reload();
                infoMessage('Job execution has been started');
            })
            .catch((error) => {
                errorMessage('Could not start job execution, error: ' + error.message);
            });
    }

    render() {
        return (
            <React.Fragment>
                <div className="long-title"><h3>Job Execution List</h3></div>
                <DataGrid
                    id={'jobTable'}
                    dataSource={jobExecutionDataSource()}
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
                                    text: 'Job Details',
                                    row: e.row.data,
                                    parent: this,
                                    onItemClick: function () {
                                        this.parent.showInfo(this.row);
                                    }
                                },
                                {
                                    template: '<hr>'
                                },
                                {
                                    text: 'Start Job',
                                    row: e.row.data,
                                    parent: this,
                                    onItemClick: function () {
                                        this.parent.startJob(this.row);
                                    }
                                },
                                {
                                    text: 'Pause Job'
                                },
                                {
                                    text: 'Stop Job'
                                },
                                {
                                    text: 'Kill Job'
                                },
                                {
                                    template: '<hr>'
                                },
                                {
                                    text: 'Delete Job',
                                    row: e.row.data,
                                    parent: this,
                                    onItemClick: function () {
                                        this.parent.deleteJob(this.row);
                                    }
                                }
                            ]
                        }
                    }}>
                    <Selection mode={'single'}/>
                    <FilterRow visible={true} applyFilter={'auto'}/>
                    <HeaderFilter visible={true}/>
                    <Sorting mode={'multiple'}/>
                    <Editing
                        mode={'popup'}
                        startEditAction={{ondblclick}}
                        useIcons={true}
                        allowDeleting={true}
                        allowUpdating={true}/>
                    <Column
                        caption={'Job Name'}
                        dataField={'jobName'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}>
                        <HeaderFilter allowSearch={true}/>
                    </Column>
                    <Column
                        caption={'Hostname'}
                        dataField={'hostName'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}>
                        <HeaderFilter allowSearch={true}/>
                    </Column>
                    <Column
                        dataField={'status'}
                        caption={'Status'}
                        dataType={'string'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={true}
                        width={90}/>
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
                        width={50}/>
                    <Column
                        dataField={'jobVersion'}
                        caption={'Version'}
                        dataType={'string'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={50}/>
                    <Column
                        dataField={'createTime'}
                        caption={'Created'}
                        dataType={'datetime'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
                    <Column
                        dataField={'startTime'}
                        caption={'Started'}
                        dataType={'datetime'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
                    <Column
                        dataField={'endTime'}
                        caption={'Ended'}
                        dataType={'datetime'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
                    <Column
                        dataField={'lastUpdated'}
                        caption={'Last Update'}
                        dataType={'datetime'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
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
                    <RemoteOperations sorting={true} paging={true}/>
                    <Paging defaultPageSize={this.state.pageSize}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                           showNavigationButtons={true} showInfo={true} visible={true}/>
                    <MasterDetail enabled={true} component={StepExecutionView}/>
                </DataGrid>
                <UpdateTimer/>
                {
                    this.state.showDetails ?
                        <JobExecutionDetails currentJobExecution={this.state.currentJobExecution}
                                             closePopup={this.toggleDetails.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }
}

export default withRouter(JobExecutionView);

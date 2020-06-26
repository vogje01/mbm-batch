import React from "react";
import './job-execution-list.scss'
import {
    Column,
    DataGrid,
    Editing,
    FilterRow,
    HeaderFilter,
    MasterDetail,
    Pager,
    Paging,
    RemoteOperations,
    Selection,
    Sorting
} from "devextreme-react/data-grid";
import {dateTimeCellTemplate, getRunningTime} from "../../utils/date-time-util";
import {JobExecutionDataSource} from "./job-execution-data-source";
import UpdateTimer, {updateIntervals} from "../../utils/update-timer";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {getItem} from "../../utils/server-connection";
import SelectBox from "devextreme-react/select-box";
import {AgentDataSource} from "../agent/agent-data-source";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";
import Button from "devextreme-react/button";
import {addFilter, clearFilter, dropFilter} from "../../utils/filter-util";
import {withRouter} from "react-router-dom";
import JobExecutionDetailsPage from "./job-execution-details";

class JobExecutionList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filterName: 'JobExecutionInfo',
            currentJobExecution: {},
            selectedStatus: null,
            selectedHost: null,
            selectedNode: null,
            selectedJobName: null
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.restartJob = this.restartJob.bind(this);
        this.onAddStatusFilter = this.onAddStatusFilter.bind(this);
        this.onAddHostNameFilter = this.onAddHostNameFilter.bind(this);
        this.onAddNodeNameFilter = this.onAddNodeNameFilter.bind(this);
        this.onAddJobNameFilter = this.onAddJobNameFilter.bind(this);
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
        if (this.props.match.params) {
            this.onAddStatusFilter({value: this.props.match.params.status})
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

    onAddJobNameFilter(e) {
        if (e.value) {
            addFilter(this.state.filterName, 'jobName', e.value);
            this.setState({selectedJobName: e.value});
        } else {
            dropFilter(this.state.filterName, 'jobName');
            this.setState({selectedJobName: null});
        }
    }

    onClearFilter() {
        clearFilter(this.state.filterName);
        this.setState({selectedStatus: undefined, selectedNode: undefined, selectedJobName: undefined});
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Executions</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item location="before">
                                <SelectBox items={this.statuses} displayExpr='key' valueExpr='value' showClearButton={true}
                                           value={this.state.selectedStatus} onValueChanged={this.onAddStatusFilter}
                                           placeholder={'Select status...'} hint={'Filter job executions by status.'}/>
                            </Item>
                            {/*<Item location="before">
                                <SelectBox dataSource={AgentDataSource()} displayExpr='hostName' valueExpr='hostName' showClearButton={true}
                                           value={this.state.selectedHost} onValueChanged={this.onAddHostNameFilter}/>
                            </Item>*/}
                            <Item location="before">
                                <SelectBox dataSource={AgentDataSource()} displayExpr='nodeName' valueExpr='nodeName' showClearButton={true}
                                           value={this.state.selectedNode} onValueChanged={this.onAddNodeNameFilter}
                                           placeholder={'Select node...'} hint={'Filter job executions by node.'}/>
                            </Item>
                            <Item location="before">
                                <SelectBox dataSource={JobDefinitionDataSource()} displayExpr={'name'} valueExpr={'name'} showClearButton={true}
                                           value={this.state.selectedJobName} onValueChanged={this.onAddJobNameFilter}
                                           placeholder={'Select job name...'} hint={'Filter job execution logs by job name.'}/>
                            </Item>
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
                                    }, hint: 'Refresh job execution list.'
                                }}/>
                            <Item
                                location="after"
                                widget="dxSelectBox"
                                locateInMenu="auto"
                                options={this.intervalSelectOptions}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={JobExecutionDataSource(this.state.filterName)}
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
                            <Editing mode={'form'} useIcons={true} allowDeleting={true}/>
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
                            <MasterDetail enabled={true} component={JobExecutionDetailsPage}/>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={80}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'restart',
                                        hint: 'Restart the job execution entry.',
                                        icon: 'material-icons-outlined ic-restart-job',
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

export default withRouter(JobExecutionList);

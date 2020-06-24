import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';
import {JobScheduleDataSource, JobScheduleStart} from "./job-schedule-data-source";
import UpdateTimer from "../../utils/update-timer";
import {Item} from "devextreme-react/autocomplete";
import {GroupItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {CustomRule, RequiredRule} from "devextreme-react/validator";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";
import JobScheduleAgentList from "./job-schedule-agent-list";
import {Toolbar} from "devextreme-react/toolbar";
import {insertItem} from "../../utils/server-connection";
import {dateTimeCellTemplate, getFormattedTime} from "../../utils/date-time-util";
import JobScheduleAgentGroupList from "./job-schedule-agentgroup-list";

class JobSchedulerList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: {},
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.cloneJobSchedule = this.cloneJobSchedule.bind(this);
        this.startJobSchedule = this.startJobSchedule.bind(this);
        this.validateType = this.validateType.bind(this);
        this.jobScheduleModes = [
            'FIXED', 'RANDOM', 'RANDOM_GROUP', 'MINIMUM_LOAD'
        ];
        this.jobScheduleTypes = [
            'CENTRAL', 'LOCAL'
        ]
    }

    selectionChanged(e) {
        this.setState({currentJobSchedule: e.data});
    }

    cloneJobSchedule(e) {
        e.event.preventDefault();
        let jobSchedule = e.row.data;
        jobSchedule.id = null;
        jobSchedule.name = jobSchedule.name + ' (copy)';
        let url = process.env.REACT_APP_MANAGER_URL + 'jobschedules/insert';
        this.setState({currentJobSchedule: insertItem(url, JSON.stringify(jobSchedule))});
    }

    startJobSchedule(e) {
        JobScheduleStart(e.row.data)
            .then(data => {
                this.setState({currentJobSchedule: data})
            });
    }

    validateType(e) {
        if (e.value === 'LOCAL' && this.state.currentJobSchedule.mode !== 'FIXED') {
            return false;
        }
        return true;
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Schedules</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }, hint: 'Refresh job schedule list.'
                                }}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={JobScheduleDataSource()}
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
                            <Editing
                                mode="form"
                                refreshMode={true}
                                useIcons={true}
                                allowUpdating={true}
                                allowAdding={true}
                                allowDeleting={true}>
                                <Form>
                                    <GroupItem colCount={4} colSpan={2} caption={"Schedule Details: " + this.state.currentJobSchedule.name}>
                                        <SimpleItem dataField="name">
                                            <RequiredRule message="Schedule name required"/>
                                            <StringLengthRule min={2} message="Schedule name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="jobDefinitionName"
                                                    editorType={'dxSelectBox'}
                                                    editorOptions={{dataSource: JobDefinitionDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
                                            <RequiredRule message="Job name required"/>
                                            <StringLengthRule min={2} message="Job name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField={'lastExecution'} editorType={'dxTextBox'}
                                                    editorOptions={{readOnly: true, value: getFormattedTime(this.state.currentJobSchedule, 'lastExecution')}}/>
                                        <SimpleItem dataField={'nextExecution'} editorType={'dxTextBox'}
                                                    editorOptions={{readOnly: true, value: getFormattedTime(this.state.currentJobSchedule, 'nextExecution')}}/>
                                        <SimpleItem dataField="schedule"/>
                                        <SimpleItem dataField="type"
                                                    editorType={'dxSelectBox'}
                                                    editorOptions={{dataSource: this.jobScheduleTypes}}>
                                            <RequiredRule message="Job schedule type is required"/>
                                            <CustomRule
                                                validationCallback={this.validateType}
                                                message="With a local scheduler type only fixed mode is allowed"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="mode"
                                                    editorType={'dxSelectBox'}
                                                    editorOptions={{dataSource: this.jobScheduleModes}}>
                                            <RequiredRule message="Job schedule mode is required"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                    </GroupItem>
                                    <GroupItem caption={"Agents"} colCount={4} visible={this.state.currentJobSchedule.name !== undefined}>
                                        <JobScheduleAgentList schedule={this.state.currentJobSchedule}/>
                                    </GroupItem>
                                    <GroupItem caption={"Agent Groups"} colCount={4} visible={this.state.currentJobSchedule.name !== undefined}>
                                        <JobScheduleAgentGroupList schedule={this.state.currentJobSchedule}/>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4} visible={this.state.currentJobSchedule.name !== undefined}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobSchedule, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobSchedule, 'modifiedAt'), readOnly: true}}/>
                                    </GroupItem>
                                </Form>
                            </Editing>
                            <Column
                                caption={'Name'}
                                dataField={'name'}
                                dataType={'string'}
                                allowEditing={true}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'jobDefinitionName'}
                                caption={'Job Definition'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'type'}
                                caption={'Schedule Type'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'mode'}
                                caption={'Schedule Mode'}
                                dataType={'string'}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                caption={'Last Execution'}
                                dataType={'datetime'}
                                dataField={'lastExecution'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                caption={'Next Execution'}
                                dataType={'datetime'}
                                dataField={'nextExecution'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                dataField={'schedule'}
                                caption={'Schedule'}
                                dataType={'string'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={120}/>
                            <Column
                                dataField={'active'}
                                caption={'Active'}
                                dataType={'boolean'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}/>
                            <Column
                                dataField={'version'}
                                caption={'Version'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'createdBy'}
                                caption={'Created By'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'createdAt'}
                                caption={'Created At'}
                                dataType={'datetime'}
                                visible={false}/>
                            <Column
                                dataField={'modifiedBy'}
                                caption={'Modified By'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'modifiedAt'}
                                caption={'Modified At'}
                                dataType={'datetime'}
                                visible={false}/>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={100}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit job schedule.',
                                        icon: 'material-icons-outlined ic-edit'
                                    },
                                    {
                                        name: 'copy',
                                        hint: 'Copy job schedule.',
                                        icon: 'material-icons-outlined ic-copy',
                                        onClick: this.cloneJobSchedule
                                    },
                                    {
                                        name: 'start',
                                        hint: 'Starts the job schedule as on demand jobs.',
                                        icon: 'material-icons-outlined ic-start',
                                        onClick: this.startJobSchedule
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job schedule.',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                            <Paging defaultPageSize={10}/>
                            <RemoteOperations sorting={true} paging={true}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobSchedulerList;

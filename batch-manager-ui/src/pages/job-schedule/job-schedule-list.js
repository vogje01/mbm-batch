import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';
import {JobScheduleDataSource} from "./job-schedule-data-source";
import UpdateTimer from "../../utils/update-timer";
import {Item} from "devextreme-react/autocomplete";
import {EmptyItem, GroupItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";
import JobScheduleAgentList from "./job-schedule-agent-list";
import {Toolbar} from "devextreme-react/toolbar";
import {insertItem} from "../../utils/server-connection";
import {getCreatedAt, getLastExecutionTime, getModifiedAt, getNextExecutionTime} from "../../utils/date-time-util";

class JobSchedulerList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: {},
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.cloneJobSchedule = this.cloneJobSchedule.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentJobSchedule: e.data});
    }

    cloneJobSchedule(e) {
        e.event.preventDefault();
        let jobSchedule = e.row.data;
        jobSchedule.id = null;
        jobSchedule.name = jobSchedule.name + ' (copy)';
        let url = process.env.REACT_APP_API_URL + 'jobschedules/insert';
        this.setState({currentJobSchedule: insertItem(url, JSON.stringify(jobSchedule))});
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
                            id={'jobScheduleTable'}
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
                                    <GroupItem caption={"Schedule Details: " + this.state.currentJobSchedule.name} colCount={2}>
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
                                                    editorOptions={{readOnly: true, value: getLastExecutionTime(this.state.currentJobSchedule)}}/>
                                        <SimpleItem dataField={'nextExecution'} editorType={'dxTextBox'}
                                                    editorOptions={{readOnly: true, value: getNextExecutionTime(this.state.currentJobSchedule)}}/>
                                        <SimpleItem dataField="schedule"/>
                                        <EmptyItem colSpan={2}/>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                    </GroupItem>
                                    <GroupItem caption={"Agents"}>
                                        <JobScheduleAgentList schedule={this.state.currentJobSchedule}/>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getCreatedAt(this.state.currentJobSchedule), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getModifiedAt(this.state.currentJobSchedule), readOnly: true}}/>
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
                                        name: 'delete',
                                        hint: 'Delete job schedule.',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
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
                                calculateCellValue={getLastExecutionTime}
                                caption={'Last Execution'}
                                dataType={'datetime'}
                                dataField={'lastExecution'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={140}/>
                            <Column
                                calculateCellValue={getNextExecutionTime}
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

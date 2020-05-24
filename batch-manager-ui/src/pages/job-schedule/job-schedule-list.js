import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';
import {JobScheduleDataSource} from "./job-schedule-data-source";
import UpdateTimer from "../../utils/update-timer";
import {Item} from "devextreme-react/autocomplete";
import {EmptyItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";
import JobScheduleAgentList from "./job-schedule-agent-list";
import {Toolbar} from "devextreme-react/toolbar";
import {insertItem} from "../../utils/server-connection";

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
                            keyExpr="id"
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
                                    <Item itemType="group" colCount={4} colSpan={4} caption={"Schedule Details: " + this.state.currentJobSchedule.name}>
                                        <SimpleItem dataField="name" colSpan={2}>
                                            <RequiredRule message="Schedule name required"/>
                                            <StringLengthRule min={2} message="Schedule name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="jobDefinitionName"
                                                    colSpan={2}
                                                    editorType={'dxSelectBox'}
                                                    editorOptions={{dataSource: JobDefinitionDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
                                            <RequiredRule message="Job name required"/>
                                            <StringLengthRule min={2} message="Job name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="lastExecution" editorOptions={{readOnly: true}} colSpan={2}/>
                                        <SimpleItem dataField="nextExecution" editorOptions={{readOnly: true}} colSpan={2}/>
                                        <SimpleItem dataField="schedule" colSpan={2}/>
                                        <EmptyItem colSpan={2}/>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                        <EmptyItem colSpan={2}/>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                                    </Item>
                                    <Item itemType="group" colCount={4} colSpan={4} caption={"Agents"}>
                                        <JobScheduleAgentList schedule={this.state.currentJobSchedule}/>
                                    </Item>
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
                                allowReordering={true}
                                width={300}/>
                            <Column
                                caption={'Last Execution'}
                                dataType={'datetime'}
                                dataField={'lastExecution'}
                                allowEditing={false}
                                allowSorting={true}
                                allowReordering={true}
                                width={160}/>
                            <Column
                                caption={'Next Execution'}
                                dataType={'datetime'}
                                dataField={'nextExecution'}
                                allowEditing={false}
                                allowSorting={true}
                                allowReordering={true}
                                width={160}/>
                            <Column
                                dataField={'schedule'}
                                caption={'Schedule'}
                                dataType={'string'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={160}/>
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
                                width={80}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit job schedule',
                                        icon: 'material-icons-outlined ic-edit'
                                    },
                                    {
                                        name: 'copy',
                                        hint: 'Copy job schedule',
                                        icon: 'material-icons-outlined ic-copy',
                                        onClick: this.cloneJobSchedule
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job schedule',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
                            <RemoteOperations
                                sorting={true}
                                paging={true}/>
                            <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                                   showNavigationButtons={true} showInfo={true} visible={true}/>
                            <Paging defaultPageSize={10}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobSchedulerList;

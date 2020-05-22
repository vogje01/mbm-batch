import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';
import {jobScheduleDataSource} from "./JobScheduleDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {Item} from "devextreme-react/autocomplete";
import {EmptyItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import JobScheduleAgentView from "./JobScheduleAgentView";
import {jobDefinitionDataSource} from "../jobdefinitions/JobDefinitionDataSource";

class JobSchedulerView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: {},
            refreshLists: {}
        };
        this.selectionChanged = this.selectionChanged.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentJobSchedule: e.data});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <div className="long-title"><h3>Job Schedule List</h3></div>
                <DataGrid
                    id={'jobScheduleTable'}
                    dataSource={jobScheduleDataSource()}
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
                                            editorOptions={{dataSource: jobDefinitionDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
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
                                <JobScheduleAgentView schedule={this.state.currentJobSchedule}/>
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
                                hint: 'Edit parameter',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete parameter',
                                icon: 'material-icons-outlined ic-delete md-18'
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
            </React.Fragment>
        );
    }
}

export default JobSchedulerView;

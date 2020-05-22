import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {agentDataSource} from "./AgentDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {Item} from "devextreme-react/autocomplete";
import {EmptyItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import AgentJobScheduleView from "./AgentJobScheduleView";

class AgentView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: {},
            currentAgents: [],
        };
        this.selectionChanged = this.selectionChanged.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentAgent: e.data});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'AgentTable'}
                    dataSource={agentDataSource()}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    onEditingStart={this.selectionChanged}
                    rowAlternationEnabled={true}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <Item itemType="group" colCount={4} colSpan={4} caption={"Agent Details: " + this.state.currentAgent.nodeName}>
                                <SimpleItem dataField="nodeName" colSpan={2}>
                                    <RequiredRule message="Node name is required"/>
                                    <StringLengthRule min={2} message="Node name must be at least 2 characters long."/>
                                </SimpleItem>
                                <SimpleItem dataField="hostName" colSpan={2}>
                                    <RequiredRule message="Host name is required"/>
                                    <StringLengthRule min={2} message="Host name must be at least 2 characters long."/>
                                </SimpleItem>
                                <SimpleItem dataField="pid" colSpan={4} edtitorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="lastStart" edtitorOptions={{readOnly: true}} colSpan={2}/>
                                <SimpleItem dataField="lastPing" edtitorOptions={{readOnly: true}} colSpan={2}/>
                                <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                <EmptyItem colSpan={2}/>
                                <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                            </Item>
                            <Item itemType="group" colCount={4} colSpan={4} caption={"Schedules"}>
                                <AgentJobScheduleView agent={this.state.currentAgent}/>
                            </Item>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Node name'}
                        dataField={'nodeName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Host name'}
                        dataField={'hostName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'PID'}
                        dataField={'pid'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={80}/>
                    <Column
                        dataField={'lastStart'}
                        caption={'Last Start'}
                        dataType={'datetime'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
                    <Column
                        dataField={'lastPing'}
                        caption={'Last Ping'}
                        dataType={'datetime'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={120}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}>
                        <FormItem editorType="dxCheckBox"/>
                    </Column>
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
                                hint: 'Edit agent definition',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete agent',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <RemoteOperations
                        sorting={true}
                        paging={true}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>
                <UpdateTimer/>
            </React.Fragment>
        );
    }

}

export default AgentView;
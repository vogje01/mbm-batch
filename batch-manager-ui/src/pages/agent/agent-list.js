import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {AgentDataSource} from "./agent-data-source";
import UpdateTimer from "../../utils/update-timer";
import {Item} from "devextreme-react/autocomplete";
import {GroupItem, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import AgentJobScheduleView from "./agent-job-schedule-list";
import {Toolbar} from "devextreme-react/toolbar";

class AgentView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: {}
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
                <h2 className={'content-block'}>Agents</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }, hint: 'Refresh agent list.'
                                }}/>
                        </Toolbar>
                        <DataGrid
                            id={'AgentTable'}
                            dataSource={AgentDataSource()}
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
                                    <GroupItem colCount={2} caption={"Agent Details: " + this.state.currentAgent.nodeName}>
                                        <SimpleItem dataField="nodeName">
                                            <RequiredRule message="Node name is required"/>
                                            <StringLengthRule min={2} message="Node name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="hostName">
                                            <RequiredRule message="Host name is required"/>
                                            <StringLengthRule min={2} message="Host name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="pid" edtitorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"}/>
                                        <SimpleItem dataField="lastStart" edtitorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="lastPing" edtitorOptions={{readOnly: true}}/>
                                    </GroupItem>
                                    <GroupItem caption={"Schedules"}>
                                        <AgentJobScheduleView agent={this.state.currentAgent}/>
                                    </GroupItem>
                                    <GroupItem caption={"Auditing"} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                                    </GroupItem>
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
                    </div>
                </div>
            </React.Fragment>
        );
    }

}

export default AgentView;
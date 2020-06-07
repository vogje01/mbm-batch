import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {AgentDataSource} from "./agent-data-source";
import UpdateTimer from "../../utils/update-timer";
import {Item} from "devextreme-react/autocomplete";
import {GroupItem, Label, SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import AgentJobScheduleView from "./agent-job-schedule-list";
import {Toolbar} from "devextreme-react/toolbar";
import {dateTimeCellTemplate, getFormattedTime} from "../../utils/date-time-util";
import {getFormattedNumber, numericCellTemplate} from "../../utils/counter-util";
import AgentAgentgroupView from "./agent-agentgroup-list";

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
                                    <GroupItem colCount={4} colSpan={2} caption={"Agent Details: " + this.state.currentAgent.nodeName}>
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
                                        <SimpleItem dataField="lastStart" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentAgent, 'lastStart'), readOnly: true}}/>
                                        <SimpleItem dataField="lastPing" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentAgent, 'lastPing'), readOnly: true}}/>
                                        <SimpleItem editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedNumber(this.state.currentAgent, 'systemLoadDay'), readOnly: true}}>
                                            <Label location={'top'} alignment={'left'} text={'System Load (Current)'}/>
                                        </SimpleItem>
                                        <SimpleItem editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedNumber(this.state.currentAgent, 'avgSystemLoadDay'), readOnly: true}}>
                                            <Label location={'top'} alignment={'left'} text={'System Load (Day)'}/>
                                        </SimpleItem>
                                        <SimpleItem editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedNumber(this.state.currentAgent, 'avgSystemLoadWeek'), readOnly: true}}>
                                            <Label location={'top'} alignment={'left'} text={'System Load (Week)'}/>
                                        </SimpleItem>
                                    </GroupItem>
                                    <GroupItem caption={"Schedules"} colCount={4} colSpan={2}>
                                        <AgentJobScheduleView agent={this.state.currentAgent}/>
                                    </GroupItem>
                                    <GroupItem caption={"Agent Groups"} colCount={4} colSpan={2}>
                                        <AgentAgentgroupView agent={this.state.currentAgent}/>
                                    </GroupItem>
                                    <GroupItem caption={"Auditing"} colSpan={2} colCount={4}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentAgent, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentAgent, 'modifiedAt'), readOnly: true}}/>
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
                                        hint: 'Edit agent.',
                                        icon: 'material-icons-outlined ic-edit'
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete agent.',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
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
                                cellTemplate={numericCellTemplate}
                                caption={'Load'}
                                dataField={'systemLoadDay'}
                                allowEditing={false}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={80}/>
                            <Column
                                cellTemplate={numericCellTemplate}
                                dataField={'avgSystemLoadDay'}
                                visible={false}/>
                            <Column
                                cellTemplate={numericCellTemplate}
                                dataField={'avgSystemLoadWeek'}
                                visible={false}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                dataField={'lastStart'}
                                caption={'Last Start'}
                                dataType={'datetime'}
                                allowEditing={false}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
                            <Column
                                cellTemplate={dateTimeCellTemplate}
                                dataField={'lastPing'}
                                caption={'Last Ping'}
                                dataType={'datetime'}
                                allowEditing={false}
                                allowSorting={true}
                                allowReordering={true}
                                allowFiltering={false}
                                width={140}/>
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
                            <Paging defaultPageSize={5}/>
                            <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                            <RemoteOperations sorting={true} paging={true}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }

}

export default AgentView;
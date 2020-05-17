import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {agentDataSource} from "./AgentDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import AgentDetails from "./AgentDetails";

class AgentView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: {},
            currentAgents: [],
            showDetails: false,
            selectedIndex: 0
        };
        this.onTabsSelectionChanged = this.onTabsSelectionChanged.bind(this);
        this.toggleDetails = this.toggleDetails.bind(this);
    }

    onTabsSelectionChanged(args) {
        if (args.name === 'selectedIndex') {
            this.setState({
                selectedIndex: args.value
            });
        }
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentAgent: e ? e.data : null
        });
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
                    onContextMenuPreparing={function (e) {
                        if (e.row.rowType === "data") {
                            e.items = [
                                {
                                    text: 'Edit agent',
                                    onClick: function () {
                                        e.component.selectRows([e.row.data]);
                                    }
                                }
                            ]
                        }
                    }}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode={'form'}
                        startEditAction={ondblclick}
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}/>
                    <Column
                        caption={'NodeName'}
                        dataField={'nodeName'}
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
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>
                <UpdateTimer/>
                {
                    this.state.showDetails ?
                        <AgentDetails currentAgent={this.state.currentAgent}
                                      closePopup={this.toggleDetails.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }

}

export default AgentView;
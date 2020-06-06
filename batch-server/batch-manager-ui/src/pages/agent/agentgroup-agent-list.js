import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {AgentGroupAgentDataSource} from "./agent-group-data-source";
import {AgentDataSource} from "./agent-data-source";
import {Label, SimpleItem} from "devextreme-react/form";

class AgentgroupAgentView extends React.Component {

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={AgentGroupAgentDataSource(this.props.agentGroup)}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
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
                            <SimpleItem
                                dataField={'id'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: AgentDataSource(), valueExpr: 'id', displayExpr: 'nodeName'}}>
                                <Label text={'Node Name'}/>
                            </SimpleItem>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Node Name'}
                        dataField={'nodeName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'ID'}
                        dataField={'id'}
                        allowEditing={true}
                        visible={false}/>
                    <Column
                        caption={'Active'}
                        dataField={'active'}
                        allowEditing={false}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'delete',
                                hint: 'Remove agent from agent group.',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                    <RemoteOperations sorting={true} paging={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }

}

export default AgentgroupAgentView;
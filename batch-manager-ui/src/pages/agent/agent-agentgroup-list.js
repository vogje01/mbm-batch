import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {AgentAgentGroupDataSource, AgentGroupDataSource} from "./agent-group-data-source";
import {SimpleItem} from "devextreme-react/form";

class AgentAgentgroupView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentAgentGroup: {},
        };
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={AgentAgentGroupDataSource(this.props.agent)}
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
                                dataField={'name'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: AgentGroupDataSource(), valueExpr: 'name', displayExpr: 'name'}}/>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Name'}
                        dataField={'name'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'delete',
                                hint: 'Remove agent group from agent.',
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

export default AgentAgentgroupView;
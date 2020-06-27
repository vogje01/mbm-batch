import React from 'react';
import {Column, DataGrid, Editing, FilterRow, Form, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {JobScheduleAgentGroupDataSource} from "./job-schedule-data-source";
import {SimpleItem} from "devextreme-react/form";
import {AgentGroupDataSource} from "../agent/agent-group-data-source";
import {Label} from "devextreme-react/circular-gauge";

class JobScheduleAgentGroupList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: props.currentJobSchedule,
        };
    }

    render() {
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={JobScheduleAgentGroupDataSource(this.props.currentJobSchedule)}
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
                    <FilterRow visible={true} applyFilter={'auto'}/>
                    <HeaderFilter visible={true}/>
                    <Sorting mode={'multiple'}/>
                    <Editing
                        mode={'form'}
                        useIcons={true}
                        allowAdding={true}
                        allowDeleting={true}
                        allowUpdating={true}>
                        <Form>
                            <SimpleItem
                                dataField={'id'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: AgentGroupDataSource(), valueExpr: 'id', displayExpr: 'name'}}>
                                <Label text={'Name'}/>
                            </SimpleItem>
                        </Form>
                    </Editing>
                    <Column
                        dataField={'id'}
                        visible={false}/>
                    <Column
                        dataField={'name'}
                        caption={'Name'}
                        dataType={'string'}
                        allowSorting={true}
                        allowReordering={true}
                        allowEditing={true}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowSorting={true}
                        allowReordering={true}
                        allowEditing={false}
                        width={80}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit agent',
                                icon: 'material-icons-outlined ic-edit'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete agent',
                                icon: 'material-icons-outlined ic-delete'
                            }
                        ]}/>
                    <RemoteOperations sorting={true} paging={true}/>
                    <Paging defaultPageSize={10}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50]}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default JobScheduleAgentGroupList;

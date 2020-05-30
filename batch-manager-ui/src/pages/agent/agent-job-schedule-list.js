import React from 'react';
import {Column, DataGrid, Editing, FilterRow, Form, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {AgentScheduleDataSource} from "./agent-data-source";
import {JobScheduleDataSource} from "../job-schedule/job-schedule-data-source";
import {SimpleItem} from "devextreme-react/form";

class AgentJobScheduleView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: this.props.data,
            schedules: [],
            selectedSchedule: {}
        };
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'jobScheduleAgentTable'}
                    dataSource={AgentScheduleDataSource(this.props.agent)}
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
                                dataField={'name'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: JobScheduleDataSource(), valueExpr: 'name', displayExpr: 'name'}}/>
                        </Form>
                    </Editing>
                    <Column
                        dataField="name"
                        caption={'Name'}
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
                                hint: 'Edit job schedule',
                                icon: 'material-icons-outlined ic-edit'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete job schedule',
                                icon: 'material-icons-outlined ic-delete'
                            }
                        ]}/>
                    <Paging defaultPageSize={5}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20]}/>
                    <RemoteOperations sorting={true} paging={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default AgentJobScheduleView;

import React from 'react';
import {Column, DataGrid, Editing, FilterRow, HeaderFilter, Lookup, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {listItems} from "../../utils/server-connection";
import {AgentScheduleDataSource} from "./agent-data-source";

class AgentJobScheduleView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentAgent: this.props.data,
            schedules: [],
            selectedSchedule: {}
        };
    }

    componentDidMount() {
        listItems('schedules', 'jobScheduleDtoes')
            .then((data) => {
                this.setState({schedules: data.data, selectedAgent: data.data[0]})
            })
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
                        startEditAction={ondblclick}
                        useIcons={true}
                        allowAdding={true}
                        allowDeleting={true}
                        allowUpdating={true}/>
                    <Column
                        dataField="name">
                        <Lookup dataSource={this.state.agents}
                                displayExpr={"nodeName"}/>
                    </Column>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowSorting={true}
                        allowReordering={true}
                        allowEditing={true}
                        width={80}/>
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
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20]}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default AgentJobScheduleView;

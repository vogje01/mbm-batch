import React from 'react';
import {Column, DataGrid, Editing, FilterRow, HeaderFilter, Lookup, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {scheduleAgentDataSource} from "./JobScheduleDataSource";
import {listItems} from "../../components/ServerConnection";

class JobScheduleAgentView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: this.props.data,
            agents: [],
            selectedAgent: {}
        };
    }

    componentDidMount() {
        listItems('agents', 'agentDtoes')
            .then((data) => {
                this.setState({agents: data.data, selectedAgent: data.data[0]})
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
                    dataSource={scheduleAgentDataSource(this.props.schedule)}
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
                        dataField="nodeName">
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

export default JobScheduleAgentView;

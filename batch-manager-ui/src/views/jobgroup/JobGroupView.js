import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import UpdateTimer from "../../components/UpdateTimer";
import JobGroupDetails from "./JobGroupDetails";
import FisPage from "../../components/FisPage";
import {JobGroupDataSource} from "./JobGroupDataSource";

class JobGroupView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobGroup: {},
            currentJobGroups: []
        };
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'jobGroupTable'}
                    dataSource={JobGroupDataSource()}
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
                        mode={'form'}
                        useIcons={true}
                        allowDeleting={true}
                        allowUpdating={true}
                        allowAdding={true}/>
                    <Column
                        caption={'Job Group Label'}
                        dataField={'label'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Job Group Name'}
                        dataField={'name'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
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
                                name: 'edit',
                                hint: 'Edit job definition',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete job definition entry',
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
                        <JobGroupDetails currentJobGroup={this.state.currentJobGroup}
                                         closePopup={this.toggleDetails.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }
}

export default JobGroupView;
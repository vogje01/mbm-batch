import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {userGroupDataSource} from "./UserGroupDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";

class UserGroupView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentUserGroup: {},
            showDetails: false
        };
        this.toggleDetails = this.toggleDetails.bind(this);
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentUser: e ? e.data : null
        });
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'UserGroupTable'}
                    dataSource={userGroupDataSource()}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}
                    style={{padding: "5px 0px 0px 0px"}}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}/>
                    <Column
                        caption={'Name'}
                        dataField={'name'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
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
                                hint: 'Edit user group',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete user group',
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
            </React.Fragment>
        );
    }

}

export default UserGroupView;
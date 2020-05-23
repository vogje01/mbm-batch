import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {UserUsergroupDataSource} from "./user-data-source";
import {UserGroupDataSource} from "./user-group-data-source";
import {SimpleItem} from "devextreme-react/form";

class UserUsergroupView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUserGroup: {},
        };
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'UserGroupTable'}
                    dataSource={UserUsergroupDataSource(this.props.user)}
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
                        allowDeleting={true}>
                        <Form>
                            <SimpleItem
                                dataField={'name'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: UserGroupDataSource(), valueExpr: 'name', displayExpr: 'name'}}/>
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
            </React.Fragment>
        );
    }

}

export default UserUsergroupView;
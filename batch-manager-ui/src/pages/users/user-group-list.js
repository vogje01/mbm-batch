import React, {useState} from "react";
import {
    Column,
    DataGrid,
    Editing,
    FilterRow,
    Form,
    Pager,
    Paging,
    RemoteOperations,
    RequiredRule,
    Selection,
    StringLengthRule
} from "devextreme-react/data-grid";
import {EmptyItem, SimpleItem} from "devextreme-react/form";
import {UserGroupDataSource} from "./user-group-data-source";
import UpdateTimer from "../../utils/update-timer";
import './user-list.scss'

export default () => {

    const [currentUserGroup, setCurrentUserGroup] = useState({});

    function selectionChanged(e) {
        setCurrentUserGroup(e.data);
    }

    return (
        <React.Fragment>
            <h2 className={'content-block'}>Users</h2>
            <div className={'content-block'}>
                <div className={'dx-card responsive-paddings'}>
                    <DataGrid
                        id={'UserGroupTable'}
                        dataSource={UserGroupDataSource()}
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
                        onEditingStart={selectionChanged}
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
                                <SimpleItem itemType="group" colCount={4} colSpan={4} caption={"User Group Details: " + currentUserGroup.name}>
                                    <SimpleItem dataField="name" colSpan={2}>
                                        <RequiredRule message="Name is required"/>
                                        <StringLengthRule min={2} message="Name must be at least 2 characters long."/>
                                    </SimpleItem>
                                    <EmptyItem colSpan={2}/>
                                    <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                                    <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                    <EmptyItem colSpan={2}/>
                                    <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                    <SimpleItem dataField="createdAt" editorOptions={{readOnly: true}}/>
                                    <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                    <SimpleItem dataField="modifiedAt" editorOptions={{readOnly: true}}/>
                                </SimpleItem>
                                <SimpleItem itemType="group" colCount={4} colSpan={4} caption={"Users"}>
                                    {/*<UsergroupUserView userGroup={currentUserGroup}/>*/}
                                </SimpleItem>
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
                            caption={'Description'}
                            dataField={'description'}
                            visible={false}/>
                        <Column
                            dataField={'active'}
                            caption={'Active'}
                            dataType={'boolean'}
                            allowEditing={true}
                            allowSorting={true}
                            allowReordering={true}
                            width={80}/>
                        <Column
                            dataField={'createdBy'}
                            caption={'Created By'}
                            dataType={'string'}
                            visible={false}/>
                        <Column
                            dataField={'createdAt'}
                            caption={'Created At'}
                            dataType={'datetime'}
                            visible={false}/>
                        <Column
                            dataField={'modifiedBy'}
                            caption={'Modified By'}
                            dataType={'string'}
                            visible={false}/>
                        <Column
                            dataField={'modifiedAt'}
                            caption={'Modified At'}
                            dataType={'datetime'}
                            visible={false}/>
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
                </div>
            </div>
        </React.Fragment>
    );
};

import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {userDataSource} from "./UserDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";

class UserView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: {},
            showDetails: false,
            refreshLists: {}
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
                    id={'UserTable'}
                    dataSource={userDataSource()}
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
                    </Editing>
                    <Column
                        caption={'UserID'}
                        dataField={'userId'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'password'}
                        caption={'Password'}
                        visible={false}
                        dataType={'string'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}>
                        <FormItem editorType="dxTextBox" editorOptions={{mode: "password"}}/>
                    </Column>
                    <Column
                        dataField={'firstName'}
                        caption={'First name'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        dataField={'lastName'}
                        caption={'Last name'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
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
                        dataField={'version'}
                        caption={'Version'}
                        dataType={'string'}
                        visible={false}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column dataField="description" visible={false}>
                        <FormItem colSpan={2} editorType="dxTextArea" editorOptions={{height: 100}}/>
                    </Column>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit user',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete user',
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

export default UserView;
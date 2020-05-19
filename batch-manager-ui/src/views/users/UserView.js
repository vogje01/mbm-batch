import React from 'react';
import Lookup, {DataGrid, RequiredRule} from "devextreme-react";
import {Column, Editing, FilterRow, FormItem, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {userDataSource} from "./UserDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {userGroupDataSource} from "./UserGroupDataSource";
import UserGroupBoxComponent from "./UserGroupBoxComponent";

const tabs = [
    {id: 'Main', text: 'Main', icon: 'material-icons-outlined ic-people md-18'},
    {id: 'Groups', text: 'Groups', icon: 'material-icons-outlined ic-group md-18'}
];

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

    cellTemplate(container, options) {
        var noBreakSpace = '\u00A0',
            text = (options.value || []).map(element => {
                return options.column.lookup.calculateCellValue(element);
            }).join(', ');
        container.textContent = text || noBreakSpace;
        container.title = text;
    }

    calculateFilterExpression(filterValue, selectedFilterOperation, target) {
        if (target === 'search' && typeof (filterValue) === 'string') {
            return [this.dataField, 'contains', filterValue];
        }
        return function (data) {
            return (data.userGroups || []).indexOf(filterValue) !== -1;
        };
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
                        dataField={'userGroups'}
                        caption={'User Groups'}
                        width={200}
                        allowSorting={false}
                        editCellComponent={UserGroupBoxComponent}
                        cellTemplate={this.cellTemplate}
                        calculateFilterExpression={this.calculateFilterExpression}>
                        <Lookup
                            dataSource={userGroupDataSource()}
                            valueExpr="id"
                            displayExpr="name"/>
                        <RequiredRule/>
                    </Column>
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
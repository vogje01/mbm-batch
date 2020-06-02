import React from "react";
import {
    Column,
    DataGrid,
    Editing,
    EmailRule,
    FilterRow,
    Form,
    Pager,
    Paging,
    PatternRule,
    RemoteOperations,
    RequiredRule,
    Selection,
    StringLengthRule
} from "devextreme-react/data-grid";
import {SimpleItem} from "devextreme-react/form";
import {UserDataSource} from "./user-data-source";
import UpdateTimer from "../../utils/update-timer";
import './user-list.scss'
import UserUsergroupView from "./user-usergroup-list";
import themes from "devextreme/ui/themes";
import {getFormattedTime} from "../../utils/date-time-util";

const themesList = [
    'material.blue.dark.compact',
    'material.blue.light.compact',
    'material.orange.dark.compact',
    'material.orange.light.compact',
    'material.lime.dark.compact',
    'material.lime.light.compact'
];

const dateFormatList = [
    {label: 'de', value: 'DE'},
    {label: 'en-gb', value: 'ENGB'},
    {label: 'en-us', value: 'ENUS'}
];

const numberFormatList = [
    {label: 'de', value: 'DE'},
    {label: 'en-gb', value: 'ENGB'},
    {label: 'en-us', value: 'ENUS'}
];

class UserList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: {}
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.themeSelectionChanged = this.themeSelectionChanged.bind(this);
        this.dateFormatValueChanged = this.dateFormatValueChanged.bind(this);
        this.numberFormatValueChanged = this.numberFormatValueChanged.bind(this);
        this.isDeleteVisible = this.isDeleteVisible.bind(this);
        this.phonePattern = /^\s*\+[0-9]{2,3}\s*-?\s*\d{3}-?\s*[0-9 ]+$/;
    }

    selectionChanged(e) {
        this.setState({currentUser: e.data});
    }

    isDeleteVisible(e) {
        return e.row.data.userId !== 'admin';
    }

    themeSelectionChanged(e) {
        themes.current(e.value);
    }

    dateFormatValueChanged(e) {
        localStorage.setItem('dateTimeFormat', e.value);
    }

    numberFormatValueChanged(e) {
        localStorage.setItem('numberFormat', e.value);
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Users</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <DataGrid
                            id={'UserTable'}
                            dataSource={UserDataSource()}
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
                            onEditingStart={this.selectionChanged}>
                            <FilterRow visible={true}/>
                            <Selection mode={'single'}/>
                            <Editing
                                mode="form"
                                useIcons={true}
                                allowUpdating={true}
                                allowAdding={true}
                                allowDeleting={true}>
                                <Form>
                                    <SimpleItem itemType="group" colCount={2} caption={"User Details: " + this.state.currentUser.userId}>
                                        <SimpleItem id={'userId'} dataField="userId">
                                            <RequiredRule message="UserId is required"/>
                                            <StringLengthRule min={5} max={7} message="UserId must be exactly 7 characters long."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="password" editorType={"dxTextBox"} editorOptions={{mode: "password"}}>
                                            <RequiredRule message="Password is required"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="firstName">
                                            <RequiredRule message="First name is required"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="lastName">
                                            <RequiredRule message="Last name is required"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="email">
                                            <EmailRule message="Email is invalid"/>
                                        </SimpleItem>
                                        <SimpleItem dataField="phone">
                                            <PatternRule message="The phone must have a correct phone format" pattern={this.phonePattern}/>
                                        </SimpleItem>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"}/>
                                        <SimpleItem
                                            dataField={'theme'}
                                            editorType={'dxSelectBox'}
                                            editorOptions={{dataSource: themesList, onSelectionChanged: this.themeSelectionChanged}}>
                                            <RequiredRule/>
                                        </SimpleItem>
                                        <SimpleItem
                                            dataField={'dateTimeFormat'}
                                            editorType={'dxSelectBox'}
                                            editorOptions={{
                                                dataSource: dateFormatList,
                                                valueExpr: 'value',
                                                displayExpr: 'label',
                                                onValueChanged: this.dateFormatValueChanged
                                            }}>
                                            <RequiredRule/>
                                        </SimpleItem>
                                        <SimpleItem
                                            dataField={'numberFormat'}
                                            editorType={'dxSelectBox'}
                                            editorOptions={{
                                                dataSource: numberFormatList,
                                                valueExpr: 'value',
                                                displayExpr: 'label',
                                                onValueChanged: this.numberFormatValueChanged
                                            }}>
                                            <RequiredRule/>
                                        </SimpleItem>
                                        <SimpleItem dataField="description" editorType="dxTextArea" colSpan={2} editorOptions={{height: 100}}/>
                                    </SimpleItem>
                                    <SimpleItem itemType="group" colSpan={2} caption={"User Groups"}>
                                        <UserUsergroupView user={this.state.currentUser}/>
                                    </SimpleItem>
                                    <SimpleItem itemType="group" colSpan={2} colCount={4} caption={"User Auditing"}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentUser, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentUser, 'modifiedAt'), readOnly: true}}/>
                                    </SimpleItem>
                                </Form>
                            </Editing>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={80}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit user',
                                        icon: 'material-icons-outlined ic-edit',
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete user',
                                        icon: 'material-icons-outlined ic-delete',
                                        visible: this.isDeleteVisible
                                    }]}/>
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
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'firstName'}
                                caption={'First name'}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}/>
                            <Column
                                dataField={'lastName'}
                                caption={'Last name'}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}/>
                            <Column
                                dataField={'email'}
                                caption={'Email'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}/>
                            <Column
                                dataField={'phone'}
                                caption={'Phone'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'theme'}
                                caption={'Theme'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'dateTimeFormat'}
                                caption={'Date Format'}
                                visible={false}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'numberFormat'}
                                caption={'Number Format'}
                                visible={false}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'active'}
                                caption={'Active'}
                                dataType={'boolean'}
                                allowReordering={true}
                                hint={'User is active.'}
                                width={80}/>
                            <Column
                                dataField={'description'}
                                caption={'Description'}
                                dataType={'string'}
                                visible={false}
                                allowSorting={true}
                                allowReordering={true}/>
                            <Column
                                dataField={'version'}
                                caption={'Version'}
                                dataType={'string'}
                                visible={false}/>
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
                            <Paging defaultPageSize={5}/>
                            <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                            <RemoteOperations sorting={true} paging={true}/>
                        </DataGrid>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default UserList;

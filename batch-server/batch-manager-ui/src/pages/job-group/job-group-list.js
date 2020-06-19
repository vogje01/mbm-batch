import React from "react";
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
import {JobGroupDataSource} from "./job-group-data-source";
import UpdateTimer from "../../utils/update-timer";
import {getFormattedTime} from "../../utils/date-time-util";
import JobGroupJobDefinitionView from "./jobgroup-jobdefinition-list";
import {Item} from "devextreme-react/autocomplete";
import {Toolbar} from "devextreme-react/toolbar";

class JobGroupList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobGroup: {}
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.isDeleteVisible = this.isDeleteVisible.bind(this);
    }

    selectionChanged(e) {
        this.setState({currentJobGroup: e.data});
    }

    // Default group cannot be deleted.
    isDeleteVisible(e) {
        return e.row.data.name !== 'Default';
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Groups</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }, hint: 'Refresh job group list.'
                                }}/>
                        </Toolbar>
                        <DataGrid
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
                                    <SimpleItem itemType="group" colCount={4} colSpan={4}
                                                caption={"Job Group Details: " + this.state.currentJobGroup.name}>
                                        <SimpleItem dataField="name" colSpan={2}>
                                            <RequiredRule message="Name is required"/>
                                            <StringLengthRule min={2} message="Name must be at least 2 characters long."/>
                                        </SimpleItem>
                                        <EmptyItem colSpan={2}/>
                                        <SimpleItem dataField="description" editorType="dxTextArea" colSpan={4} editorOptions={{height: 100}}/>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                                        <EmptyItem colSpan={2}/>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobGroup, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobGroup, 'modifiedAt'), readOnly: true}}/>
                                    </SimpleItem>
                                    <SimpleItem itemType="group" colCount={4} colSpan={4} caption={"Job Definitions"}>
                                        <JobGroupJobDefinitionView jobGroup={this.state.currentJobGroup}/>
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
                                        hint: 'Edit jobGroup group',
                                        icon: 'material-icons-outlined ic-edit',
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete jobGroup group',
                                        icon: 'material-icons-outlined ic-delete',
                                        visible: this.isDeleteVisible
                                    }]}/>
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

export default JobGroupList

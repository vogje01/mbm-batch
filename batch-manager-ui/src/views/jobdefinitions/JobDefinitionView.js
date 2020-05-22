import React from 'react';
import {DataGrid, Menu} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Lookup, Pager, Paging, RemoteOperations, RequiredRule, Selection, StringLengthRule} from "devextreme-react/data-grid";
import UpdateTimer from "../../components/UpdateTimer";
import JobDefinitionDetails from "./JobDefinitionDetails";
import JobDefinitionExport from "./JobDefinitionExport";
import JobDefinitionImport from "./JobDefinitionImport";
import FisPage from "../../components/FisPage";
import {jobDefinitionDataSource} from "./JobDefinitionDataSource";
import {PatternRule, SimpleItem} from "devextreme-react/form";
import {JobGroupDataSource} from "../jobgroup/JobGroupDataSource";
import {Item} from "devextreme-react/autocomplete";

const types = [
    {type: 'JAR', name: 'JAR'},
    {type: 'DOCKER', name: 'DOCKER'}
];

const renderJobGroupSelectBoxItem = item => {
    return <div>{item.name}</div>;
}

class JobDefinitionView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: {},
            currentJobDefinitions: [],
            showExport: false,
            showImport: false
        };
        this.toggleExport = this.toggleExport.bind(this);
        this.toggleImport = this.toggleImport.bind(this);
        this.selectionChanged = this.selectionChanged.bind(this);
        this.onMenuItemClick = this.onMenuItemClick.bind(this);
        this.menus = [{
            id: '1',
            name: 'File',
            items: [{
                id: '1_1',
                name: 'Export Job Definitions',
                icon: 'material-icons-outlined ic-import-export md-18'
            }, {
                id: '1_2',
                name: 'Import Job Definitions',
                icon: 'material-icons-outlined ic-import-export md-18'
            }]
        }];
        this.versionPattern = /^\s*\d+\.\d+\.\d+\s*$/;
    }

    toggleExport(e) {
        this.setState({
            showExport: !this.state.showExport
        });
    }

    toggleImport(e) {
        this.setState({
            showImport: !this.state.showImport
        });
    }

    onMenuItemClick(e) {
        if (e.itemData.id === '1_1') {
            this.setState({showExport: !this.state.showExport});
        } else if (e.itemData.id === '1_2') {
            this.setState({showImport: !this.state.showImport});
        }
    }

    selectionChanged(e) {
        this.setState({currentJobDefinition: e.data});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <Menu dataSource={this.menus}
                      displayExpr={'name'}
                      showFirstSubmenuMode={{
                          name: 'onHover',
                          delay: {show: 0, hide: 500}
                      }}
                      orientation={'horizontal'}
                      submenuDirection={'auto'}
                      hideSubmenuOnMouseLeave={false}
                      onItemClick={this.onMenuItemClick}/>
                <DataGrid
                    id={'jobDefinitionTable'}
                    dataSource={jobDefinitionDataSource()}
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
                    onEditingStart={this.selectionChanged}
                    /*onContextMenuPreparing={function (e) {
                        if (e.row.rowType === "data") {
                            e.items = [
                                {
                                    text: 'Edit job definition',
                                    onClick: function () {
                                        e.component.selectRows([e.row.data]);
                                    }
                                },
                                {
                                    text: 'Start immediately',
                                    onItemClick: function () {
                                        fetch(e.row.data._links.start.href).then(() => {
                                            jobDefinitionDataSource().reload();
                                            infoMessage('Job execution of job ' + e.row.data.name + ' has been started');
                                        }).catch(() => {
                                            errorMessage('Could not start job ');
                                        });
                                    }
                                }
                            ]
                        }
                    }}*/>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <Item itemType="group" colCount={4} colSpan={4} caption={"User Details: " + this.state.currentJobDefinition.label}>
                                <SimpleItem dataField="label" colSpan={2}>
                                    <StringLengthRule max={256} message="Labels must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem dataField="name" colSpan={2}>
                                    <RequiredRule/>
                                    <StringLengthRule max={256} message="Name must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem
                                    colSpan={2}
                                    dataField={'jobGroupName'}
                                    editorType={'dxSelectBox'}
                                    editorOptions={{dataSource: JobGroupDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem dataField="jobVersion" colSpan={2}>
                                    <RequiredRule/>
                                    <StringLengthRule min={5} max={32} message="Version must be less than 32 characters."/>
                                    <PatternRule pattern={this.versionPattern} message="Version must have correct format."/>
                                </SimpleItem>
                                <SimpleItem dataField="type" editorOptions={{dataSource: types, valueExpr: 'type', displayExpr: 'name'}} colSpan={2}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem dataField="command" colSpan={2}>
                                    <RequiredRule/>
                                    <StringLengthRule max={256} message="Command must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem dataField="fileName" colSpan={2}>
                                    <RequiredRule/>
                                    <StringLengthRule max={256} message="File name must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem dataField="workingDirectory" colSpan={2}>
                                    <RequiredRule/>
                                    <StringLengthRule max={256} message="Command must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem dataField="active" editorType={"dxCheckBox"}>
                                    <RequiredRule/>
                                </SimpleItem>
                            </Item>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Job Label'}
                        dataField={'label'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Job Name'}
                        dataField={'name'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Group Name'}
                        dataField={'jobGroupName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'jobVersion'}
                        caption={'Job Version'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={50}/>
                    <Column
                        dataField={'type'}
                        caption={'Type'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}>
                        <Lookup dataSource={types} valueExpr="type" displayExpr="name"/>
                    </Column>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        dataField={'fileName'}
                        caption={'File'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={160}/>
                    <Column
                        dataField={'command'}
                        caption={'Command'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'workingDirectory'}
                        caption={'Working Dir.'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'description'}
                        caption={'Description'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit agent definition',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete agent',
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
                        <JobDefinitionDetails currentJobDefinition={this.state.currentJobDefinition}
                                              closePopup={this.toggleDetails.bind(this)}/> : null
                }
                {
                    this.state.showExport ? <JobDefinitionExport closePopup={this.toggleExport.bind(this)}/> : null
                }
                {
                    this.state.showExport ? <JobDefinitionImport closePopup={this.toggleImport.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }
}

export default JobDefinitionView;
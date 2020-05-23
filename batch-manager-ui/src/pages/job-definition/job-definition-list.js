import React from 'react';
import './job-definition-list.scss'
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Lookup, Pager, Paging, RemoteOperations, RequiredRule, Selection, StringLengthRule} from "devextreme-react/data-grid";
import UpdateTimer from "../../utils/update-timer";
import {PatternRule, SimpleItem} from "devextreme-react/form";
import {Item} from "devextreme-react/autocomplete";
import {Toolbar} from "devextreme-react/toolbar";
import {jobDefinitionDataSource} from "./job-definition-data-source";
import {JobGroupDataSource} from "../job-group/job-group-data-source";
import JobDefinitionExport from "./job-definition-export";
import JobDefinitionImport from "./job-definition-import";
import {insertItem} from "../../utils/server-connection";

const types = [
    {type: 'JAR', name: 'JAR'},
    {type: 'DOCKER', name: 'DOCKER'}
];

class JobDefinitionList extends React.Component {

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
        this.cloneJobDefinition = this.cloneJobDefinition.bind(this);
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

    selectionChanged(e) {
        this.setState({currentJobDefinition: e.data});
    }

    cloneJobDefinition(e) {
        e.event.preventDefault();
        let jobDefinition = e.row.data;
        jobDefinition.id = null;
        jobDefinition.name = jobDefinition.name + ' (copy)';
        jobDefinition.label = jobDefinition.label + ' (copy)';
        let url = process.env.REACT_APP_API_URL + 'jobdefinitions/insert';
        this.setState({currentJobDefinition: insertItem(url, JSON.stringify(jobDefinition))});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Definitions</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-refresh", onClick: () => {
                                        this.setState({})
                                    }
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-import", onClick: () => {
                                        this.toggleImport()
                                    }
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-export", onClick: () => {
                                        this.toggleExport()
                                    }
                                }}/>
                        </Toolbar>
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
                                    <Item itemType="group" colCount={4} colSpan={4}
                                          caption={"Job Definition Details: " + this.state.currentJobDefinition.label}>
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
                                width={80}/>
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
                                visible={false}
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
                                        hint: 'Edit job definition',
                                        icon: 'material-icons-outlined ic-edit'
                                    },
                                    {
                                        name: 'copy',
                                        hint: 'Copy job definition',
                                        icon: 'material-icons-outlined ic-copy',
                                        onClick: this.cloneJobDefinition
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job definition',
                                        icon: 'material-icons-outlined ic-delete'
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
                            this.state.showExport ? <JobDefinitionExport closePopup={this.toggleExport.bind(this)}/> : null
                        }
                        {
                            this.state.showExport ? <JobDefinitionImport closePopup={this.toggleImport.bind(this)}/> : null
                        }
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionList;
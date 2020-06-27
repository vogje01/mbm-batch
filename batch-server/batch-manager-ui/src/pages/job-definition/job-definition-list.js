import React from 'react';
import './job-definition-list.scss'
import DataGrid, {
    Column,
    Editing,
    FilterRow,
    Form,
    Lookup,
    MasterDetail,
    Pager,
    Paging,
    RemoteOperations,
    RequiredRule,
    Selection,
    StringLengthRule
} from "devextreme-react/data-grid";
import UpdateTimer from "../../utils/update-timer";
import {EmptyItem, GroupItem, PatternRule, SimpleItem} from "devextreme-react/form";
import Toolbar, {Item} from "devextreme-react/toolbar";
import Popup from "devextreme-react/popup";
import {JobDefinitionDataSource, JobStart} from "./job-definition-data-source";
import {getItem, insertItem} from "../../utils/server-connection";
import JobDefinitionParamList from "./job-definition-param-list";
import {getFormattedTime} from "../../utils/date-time-util";
import JobDefinitionJobGroupList from "./job-definition-job-group-list";
import {AgentDataSource} from "../agent/agent-data-source";
import SelectBox from "devextreme-react/select-box";
import Button from "devextreme-react/button";
import JobDefinitionDetailsPage from "./job-definition-details";

const types = [
    {type: 'JAR', name: 'JAR'},
    {type: 'DOCKER', name: 'DOCKER'},
    {type: 'SCRIPT', name: 'SCRIPT'},
    {type: 'INTERNAL', name: 'INTERNAL'}
];

class JobDefinitionList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: {},
            currentAgent: {},
            jobGroups: [],
            agentPopupVisible: false,
            showExport: false,
            showImport: false
        };
        this.toggleExport = this.toggleExport.bind(this);
        this.toggleImport = this.toggleImport.bind(this);
        this.uploadFile = this.uploadFile.bind(this);
        this.hideAgentPopup = this.hideAgentPopup.bind(this);
        this.showAgentPopup = this.showAgentPopup.bind(this);
        this.selectionChanged = this.selectionChanged.bind(this);
        this.cloneJobDefinition = this.cloneJobDefinition.bind(this);
        this.startJobDefinition = this.startJobDefinition.bind(this);
        this.agentSelectionChanged = this.agentSelectionChanged.bind(this);
        this.versionPattern = /^\s*\d+\.\d+\.\d+\s*$/;
    }

    componentDidMount() {
        getItem(process.env.REACT_APP_MANAGER_URL + 'jobgroups?page=0&size=-1&sortBy=name&sortDir=asc')
            .then((data) => {
                this.setState({jobGroups: data._embedded.jobGroupDtoes})
            });
    }

    toggleExport(e) {
        this.props.history.push('/jobdefinitionexport')
    }

    toggleImport(e) {
        this.props.history.push('/jobdefinitionimport')
    }

    uploadFile(e) {
        this.props.history.push('/jobfileupload')
    }

    showAgentPopup(e) {
        this.setState({
            currentJobDefinition: e.row.data,
            agentPopupVisible: true
        });
    }

    hideAgentPopup() {
        this.setState({agentPopupVisible: false})
    }

    selectionChanged(e) {
        this.setState({currentJobDefinition: e.data});
    }

    agentSelectionChanged(e) {
        this.setState({currentAgent: e.selectedItem});
    }

    cloneJobDefinition(e) {
        e.event.preventDefault();
        let jobDefinition = e.row.data;
        jobDefinition.id = null;
        jobDefinition.name = jobDefinition.name + ' (copy)';
        jobDefinition.label = jobDefinition.label + ' (copy)';
        let url = process.env.REACT_APP_MANAGER_URL + 'jobdefinitions/insert';
        this.setState({currentJobDefinition: insertItem(url, JSON.stringify(jobDefinition))});
    }

    startJobDefinition(e) {
        JobStart(this.state.currentJobDefinition, this.state.currentAgent)
            .then(data => {
                this.setState({currentJobDefinition: data, agentPopupVisible: false})
            });
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
                                    icon: "material-icons-outlined ic-refresh", hint: 'Refresh the job definition list', onClick: () => {
                                        this.setState({})
                                    }
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-import", hint: 'Import job definition list', onClick: () => {
                                        this.toggleImport()
                                    }
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-export", hint: 'Export job definition list', onClick: () => {
                                        this.toggleExport();
                                    }
                                }}/>
                            <Item
                                location="before"
                                widget="dxButton"
                                options={{
                                    icon: "material-icons-outlined ic-upload", hint: 'Upload a batch job to the server', onClick: () => {
                                        this.uploadFile();
                                    }
                                }}/>
                        </Toolbar>
                        <DataGrid
                            dataSource={JobDefinitionDataSource()}
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
                                    <GroupItem colCount={2} caption={"Job Definition Details: " + this.state.currentJobDefinition.label}>
                                        <SimpleItem dataField="label">
                                            <StringLengthRule max={256} message="Labels must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="name">
                                            <RequiredRule/>
                                            <StringLengthRule max={256} message="Name must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="jobVersion">
                                            <RequiredRule/>
                                            <StringLengthRule min={5} max={32} message="Version must be less than 32 characters."/>
                                            <PatternRule pattern={this.versionPattern} message="Version must have correct format."/>
                                        </SimpleItem>
                                        <SimpleItem
                                            dataField={'jobGroupId'}
                                            editorType={'dxSelectBox'}
                                            editorOptions={{dataSource: this.state.jobGroups, valueExpr: 'id', displayExpr: 'name'}}>
                                            <RequiredRule/>
                                        </SimpleItem>
                                        <SimpleItem dataField="active" editorType={"dxCheckBox"}/>
                                        <EmptyItem/>
                                        <SimpleItem dataField="fileSize" editorType={"dxTextBox"} editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="fileHash" editorType={"dxTextBox"} editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="description" colSpan={2} editorType={'dxTextArea'} editorOptions={{height: 90}}/>
                                    </GroupItem>
                                    <GroupItem colCount={2} caption={"Command"}>
                                        <SimpleItem dataField="type" editorOptions={{dataSource: types, valueExpr: 'type', displayExpr: 'name'}}>
                                            <RequiredRule/>
                                        </SimpleItem>
                                        <EmptyItem/>
                                        <SimpleItem dataField="command">
                                            <RequiredRule/>
                                            <StringLengthRule max={256} message="Command must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="fileName">
                                            <RequiredRule/>
                                            <StringLengthRule max={256} message="File name must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="workingDirectory">
                                            <RequiredRule/>
                                            <StringLengthRule max={256} message="Working directory must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="loggingDirectory">
                                            <RequiredRule/>
                                            <StringLengthRule max={256} message="Logging directory must be less than 256 characters."/>
                                        </SimpleItem>
                                        <SimpleItem dataField="failedExitCode"/>
                                        <SimpleItem dataField="failedExitMessage"/>
                                        <SimpleItem dataField="completedExitCode"/>
                                        <SimpleItem dataField="completedExitMessage"/>
                                    </GroupItem>
                                    <GroupItem caption={'Job Groups'} colSpan={2} colCount={4} visible={this.state.currentJobDefinition.name !== undefined}>
                                        <JobDefinitionJobGroupList jobDefinition={this.state.currentJobDefinition}/>
                                    </GroupItem>
                                    <GroupItem caption={'Parameter'} colSpan={2} colCount={4} visible={this.state.currentJobDefinition.name !== undefined}>
                                        <JobDefinitionParamList jobDefinition={this.state.currentJobDefinition}/>
                                    </GroupItem>
                                    <GroupItem caption={'Auditing'} colSpan={2} colCount={4} visible={this.state.currentJobDefinition.name !== undefined}>
                                        <SimpleItem dataField="createdBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="createdAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobDefinition, 'createdAt'), readOnly: true}}/>
                                        <SimpleItem dataField="modifiedBy" editorOptions={{readOnly: true}}/>
                                        <SimpleItem dataField="modifiedAt" editorType="dxTextBox"
                                                    editorOptions={{value: getFormattedTime(this.state.currentJobDefinition, 'modifiedAt'), readOnly: true}}/>
                                    </GroupItem>
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
                                dataField={'jobGroupId'}
                                allowEditing={true}
                                allowFiltering={true}
                                allowSorting={true}
                                allowReordering={true}>
                                <Lookup
                                    dataSource={this.state.jobGroups}
                                    valueExpr={'id'}
                                    displayExpr={'name'}/>
                            </Column>
                            <Column
                                dataField={'jobVersion'}
                                caption={'Job Version'}
                                dataType={'string'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}/>
                            <Column
                                dataField={'jobGroupId'}
                                caption={'Main Group'}
                                dataType={'string'}
                                allowEditing={true}
                                allowSorting={true}
                                allowReordering={true}
                                width={80}
                                visible={false}/>
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
                                visible={false}/>
                            <Column
                                dataField={'loggingDirectory'}
                                caption={'Logging Dir.'}
                                visible={false}/>
                            <Column
                                dataField={'description'}
                                caption={'Description'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'failedExitCode'}
                                caption={'Failed Exit Code'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'failedExitMessage'}
                                caption={'Failed Exit Message'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'completedExitCode'}
                                caption={'Completed Exit Code'}
                                dataType={'string'}
                                visible={false}/>
                            <Column
                                dataField={'completedExitMessage'}
                                caption={'Completed Exit Message'}
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
                            <Column
                                dataField={'fileSize'}
                                dataType={'number'}
                                visible={false}/>
                            <Column
                                dataField={'fileHash'}
                                dataType={'string'}
                                visible={false}/>
                            <Paging defaultPageSize={10}/>
                            <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                            <RemoteOperations sorting={true} paging={true}/>
                            <Column
                                allowSorting={false}
                                allowReordering={false}
                                width={100}
                                type={'buttons'}
                                buttons={[
                                    {
                                        name: 'edit',
                                        hint: 'Edit job definition.',
                                        icon: 'material-icons-outlined ic-edit'
                                    },
                                    {
                                        name: 'copy',
                                        hint: 'Clone job definition.',
                                        icon: 'material-icons-outlined ic-copy',
                                        onClick: this.cloneJobDefinition
                                    },
                                    {
                                        name: 'start',
                                        hint: 'Starts the job as on demand job.',
                                        icon: 'material-icons-outlined ic-on-demand',
                                        onClick: this.showAgentPopup
                                    },
                                    {
                                        name: 'delete',
                                        hint: 'Delete job definition.',
                                        icon: 'material-icons-outlined ic-delete'
                                    }
                                ]}/>
                            <MasterDetail enabled={true} component={JobDefinitionDetailsPage}/>
                        </DataGrid>
                        <UpdateTimer/>
                        <Popup
                            visible={this.state.agentPopupVisible}
                            onHiding={this.hideAgentPopup}
                            dragEnabled={false}
                            closeOnOutsideClick={true}
                            showTitle={true}
                            title="Start on demand job"
                            width={300}
                            height={200}>
                            <div className="popup-property-details">
                                <span>Agent:</span>
                                <SelectBox
                                    dataSource={AgentDataSource()}
                                    valueExpr={'id'}
                                    displayExpr={'nodeName'}
                                    placeholder={'Select an agent...'}
                                    onSelectionChanged={this.agentSelectionChanged}/>
                                <Button
                                    style={{verticalAlignment: 'center', marginTop: '20px', marginRight: '20px'}}
                                    horizontalAlignment={'center'}
                                    text={'Start'}
                                    type={'success'}
                                    onClick={this.startJobDefinition}/>
                                <Button
                                    style={{verticalAlignment: 'center', marginTop: '20px', marginRight: '20px'}}
                                    horizontalAlignment={'center'}
                                    text={'Cancel'}
                                    type={'success'}
                                    onClick={this.hideAgentPopup}/>
                            </div>
                        </Popup>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionList;
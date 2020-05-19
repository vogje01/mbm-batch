import React from 'react';
import {DataGrid, Menu} from "devextreme-react";
import {Column, Editing, FilterRow, Lookup, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {filter} from "rxjs/operators";
import {refreshSubject} from "../../components/MainComponent";
import {errorMessage, infoMessage} from "../../util/MessageUtil";
import UpdateTimer from "../../components/UpdateTimer";
import JobDefinitionDetails from "./JobDefinitionDetails";
import JobDefinitionExport from "./JobDefinitionExport";
import JobDefinitionImport from "./JobDefinitionImport";
import FisPage from "../../components/FisPage";
import {jobDefinitionDataSource} from "./JobDefinitionDataSource";

const types = [
    {type: 'JAR', name: 'JAR'},
    {type: 'DOCKER', name: 'DOCKER'}
];

class JobDefinitionView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: {},
            currentJobDefinitions: [],
            showDetails: false,
            showExport: false,
            showImport: false
        };
        this.toggleDetails = this.toggleDetails.bind(this);
        this.toggleExport = this.toggleExport.bind(this);
        this.toggleImport = this.toggleImport.bind(this);
        this.onMenuItemClick = this.onMenuItemClick.bind(this);
        this.unsub = refreshSubject
            .pipe(filter(f => f.topic === 'Refresh'))
            .subscribe(() => this.setState({refreshLists: {}}));
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
    }

    componentWillUnmount() {
        this.unsub.unsubscribe()
    }

    shouldComponentUpdate(nextProps, nextStatenext, nextContext) {
        jobDefinitionDataSource().reload();
        return true;
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentJobDefinition: e ? e.data : null
        });
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
                    keyExpr={'id'}
                    dataSource={jobDefinitionDataSource()}
                    hoverStateEnabled={true}
                    //onRowDblClick={this.toggleDetails}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}
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
                        allowDeleting={true}/>
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
                        dataField={'groupName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'jobVersion'}
                        caption={'Version'}
                        dataType={'string'}
                        allowEditing={false}
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
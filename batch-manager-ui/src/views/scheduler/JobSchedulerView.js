import React from 'react';

import 'devextreme/data/odata/store';
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from 'devextreme-react/data-grid';

import {refreshSubject} from "../../components/MainComponent";
import {filter} from "rxjs/operators";
import {jobScheduleDataSource} from "./JobScheduleDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import JobScheduleDetails from "./JobScheduleDetails";
import FisPage from "../../components/FisPage";
import {SimpleItem, StringLengthRule} from "devextreme-react/form";
import {RequiredRule} from "devextreme-react/validator";
import {JobGroupDataSource} from "../jobgroup/JobGroupDataSource";

class JobSchedulerView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: {},
            refreshLists: {}
        };
        this.toggleDetails = this.toggleDetails.bind(this);
        this.unsub = refreshSubject
            .pipe(filter(f => f.topic === 'Refresh'))
            .subscribe(s => this.setState({refreshLists: {}}));
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentJobSchedule: e ? e.data : null
        });
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <div className="long-title"><h3>Job Schedule List</h3></div>
                <DataGrid
                    id={'jobScheduleTable'}
                    dataSource={jobScheduleDataSource()}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        refreshMode={true}
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <SimpleItem id={'jobName'} dataField="name">
                                <RequiredRule message="Name is required"/>
                                <StringLengthRule max={256} message="Name must be less than 256 characters."/>
                            </SimpleItem>
                            <SimpleItem
                                dataField={'groupName'}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: JobGroupDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
                                <RequiredRule message="Group name is required"/>
                            </SimpleItem>
                            <SimpleItem id={'schedule'} dataField="schedule">
                                <RequiredRule message="Schedule is required"/>
                                <StringLengthRule max={256} message="Schedule must be less than 256 characters."/>
                            </SimpleItem>
                            <SimpleItem dataField="active" editorType={"dxCheckBox"}/>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Job Name'}
                        dataField={'name'}
                        dataType={'string'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        dataField={'groupName'}
                        caption={'Group'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={100}/>
                    <Column
                        caption={'Last Execution'}
                        dataType={'datetime'}
                        dataField={'lastExecution'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}
                        width={160}/>
                    <Column
                        caption={'Next Execution'}
                        dataType={'datetime'}
                        dataField={'nextExecution'}
                        allowEditing={false}
                        allowSorting={true}
                        allowReordering={true}
                        width={160}/>
                    <Column
                        dataField={'schedule'}
                        caption={'Schedule'}
                        dataType={'string'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={160}/>
                    <Column
                        dataField={'active'}
                        caption={'Active'}
                        dataType={'boolean'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={80}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit parameter',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete parameter',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <RemoteOperations
                        sorting={true}
                        paging={true}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                           showNavigationButtons={true} showInfo={true} visible={true}/>
                    <Paging defaultPageSize={5}/>
                </DataGrid>
                <UpdateTimer/>
                {this.state.showDetails ? <JobScheduleDetails currentJobSchedule={this.state.currentJobSchedule}
                                                              closePopup={this.toggleDetails.bind(this)}/> : null
                }
            </React.Fragment>
        );
    }
}

export default JobSchedulerView;

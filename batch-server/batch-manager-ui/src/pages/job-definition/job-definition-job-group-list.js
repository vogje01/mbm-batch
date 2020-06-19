import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {JobDefinitionJobGroupDataSource} from "./job-definition-data-source";
import {JobGroupDataSource} from "../job-group/job-group-data-source";
import {SimpleItem} from "devextreme-react/form";
import {Label} from "devextreme-react/bar-gauge";

// Display the job definition job group relationship.
//
class JobDefinitionJobGroupList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: {},
        };
    }

    render() {
        if (this.props.jobDefinition.name === undefined) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={JobDefinitionJobGroupDataSource(this.props.jobDefinition)}
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
                        <Form>
                            <SimpleItem
                                dataField={'id'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: JobGroupDataSource(), valueExpr: 'id', displayExpr: 'name'}}>
                                <Label text={'Name'}/>
                            </SimpleItem>
                        </Form>
                    </Editing>
                    <Column
                        dataField={'id'}
                        visible={false}/>
                    <Column
                        caption={'Name'}
                        dataField={'name'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'delete',
                                hint: 'Remove job group from job definition.',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                    <RemoteOperations sorting={true} paging={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }

}

export default JobDefinitionJobGroupList;
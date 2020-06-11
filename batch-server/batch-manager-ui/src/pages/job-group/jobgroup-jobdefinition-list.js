import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Form, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {JobDefinitionRestrictedDataSource, JobGroupJobDefinitionDataSource} from "./job-group-data-source";
import {Label, SimpleItem} from "devextreme-react/form";

class JobGroupJobDefinitionView extends React.Component {

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={JobGroupJobDefinitionDataSource(this.props.jobGroup)}
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
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <SimpleItem
                                dataField={'id'}
                                isRequired={true}
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: JobDefinitionRestrictedDataSource(this.props.jobGroup), valueExpr: 'id', displayExpr: 'name'}}>
                                <Label text={'JobDefinitionID'}/>
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
                                hint: 'Remove job definition from job group.',
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

export default JobGroupJobDefinitionView;
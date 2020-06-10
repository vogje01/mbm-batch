import React from 'react';
import {CheckBox} from "devextreme-react";
import DataGrid, {Column, Editing, FilterRow, Form, Pager, Paging, RequiredRule, Selection, StringLengthRule} from 'devextreme-react/data-grid';
import {JobDefinitionParamDataSource} from "./job-definition-data-source";
import {GroupItem, SimpleItem} from "devextreme-react/form";

/**
 * @return {null}
 */
function JobDefinitionParamValueTemplate(cellElement, cell) {
    const type = cellElement.data.data.type;
    switch (type) {
        case 'STRING':
            return (<span>{cellElement.data.data.stringValue}</span>);
        case 'LONG':
            return (<span>{cellElement.data.data.longValue}</span>);
        case 'DOUBLE':
            return (<span>{cellElement.data.data.doubleValue}</span>);
        case 'BOOLEAN':
            return (
                <div className="dx-field-value"><CheckBox value={cellElement.data.data.booleanValue} visible={true} onValueChanged={
                    function (e) {
                        if (e.event) e.event.stopPropagation();
                        cellElement.data.data.booleanValue = e.value;

                    }}/></div>);
        default:
            return null;
    }
}

function JobDefinitionParamValueRender(cell) {
    const {type} = cell.data;
    switch (type) {
        case 'STRING':
            return (<span>{cell.row.data.stringValue}</span>);
        case 'LONG':
            return (<span>{cell.row.data.longValue}</span>);
        case 'DOUBLE':
            return (<span>{cell.row.data.doubleValue}</span>);
        case 'BOOLEAN':
            return <CheckBox value={cell.row.data.booleanValue}/>;
        default:
            return null;
    }
}

class JobDefinitionParamList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.jobDefinition,
            currentJobDefinitionParam: {},
            typeEditable: false
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.onRowInserted = this.onRowInserted.bind(this);
        this.paramTypes = [
            {label: 'String', type: 'STRING'},
            {label: 'Long', type: 'LONG'},
            {label: 'Double', type: 'DOUBLE'},
            {label: 'Boolean', type: 'BOOLEAN'},
            {label: 'Date', type: 'DATE'}
        ];
    }


    onRowInserted(e) {
        this.setState({currentJobDefinitionParam: {type: 'LONG'}, typeEditable: true});
    }

    selectionChanged(e) {
        this.setState({currentJobDefinitionParam: e.data});
    }

    render() {
        return (
            <React.Fragment>
                <DataGrid
                    dataSource={JobDefinitionParamDataSource(this.props.jobDefinition)}
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
                    onInitNewRow={this.onRowInserted}
                    onEditingStart={this.selectionChanged}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode={'form'}
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <Form>
                            <GroupItem colCount={3} caption={"Job Definition Parameter Details: " + this.state.currentJobDefinitionParam.keyName}>
                                <SimpleItem dataField="keyName">
                                    <RequiredRule/>
                                    <StringLengthRule min={1} max={256} message="Name must be less than 256 characters."/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'type'}
                                    editorType={'dxSelectBox'}
                                    editorOptions={{dataSource: this.paramTypes, valueExpr: 'type', displayExpr: 'label'}}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'longValue'}
                                    editorType={'dxTextBox'}
                                    visible={this.state.currentJobDefinitionParam.type === 'LONG'}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'booleanValue'}
                                    editorType={'dxCheckBox'}
                                    visible={this.state.currentJobDefinitionParam.type === 'BOOLEAN'}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'stringValue'}
                                    editorType={'dxTextBox'}
                                    visible={this.state.currentJobDefinitionParam.type === 'STRING'}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'doubleValue'}
                                    editorType={'dxTextBox'}
                                    visible={this.state.currentJobDefinitionParam.type === 'DOUBLE'}>
                                    <RequiredRule/>
                                </SimpleItem>
                                <SimpleItem
                                    dataField={'dateValue'}
                                    editorType={'dxDateBox'}
                                    visible={this.state.currentJobDefinitionParam.type === 'DATE'}>
                                    <RequiredRule/>
                                </SimpleItem>
                            </GroupItem>
                        </Form>
                    </Editing>
                    <Column
                        caption={'Name'}
                        dataField={'keyName'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Type'}
                        dataField={'type'}
                        allowEditing={this.state.typeEditable}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}>
                    </Column>
                    <Column
                        calculateCellValue={this.getValue}
                        caption={'Value'}
                        dataField={'value'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}
                        cellRender={JobDefinitionParamValueRender}
                        editCellComponent={JobDefinitionParamValueTemplate}/>
                    <Column
                        caption={'Value'}
                        dataField={'longValue'}
                        visible={false}/>
                    <Column
                        caption={'Value'}
                        dataField={'stringValue'}
                        visible={false}/>
                    <Column
                        caption={'Value'}
                        dataField={'dateValue'}
                        visible={false}/>
                    <Column
                        caption={'Value'}
                        dataField={'doubleValue'}
                        visible={false}/>
                    <Column
                        caption={'Value'}
                        dataField={'booleanValue'}
                        visible={false}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit job definition parameter',
                                icon: 'material-icons-outlined ic-edit'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete job definition parameter',
                                icon: 'material-icons-outlined ic-delete'
                            }
                        ]}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default JobDefinitionParamList;

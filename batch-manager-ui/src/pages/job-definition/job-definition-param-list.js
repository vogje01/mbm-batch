import React from 'react';
import {CheckBox} from "devextreme-react";
import DataGrid, {Column, Editing, FilterRow, Pager, Paging, Selection} from 'devextreme-react/data-grid';
import {errorMessage, infoMessage} from "../../utils/message-util";
import {JobDefinitionParamDataSource} from "./job-definition-data-source";
import JobDefinitionParamForm from "./job-definition-param-form";

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
            currentJobDefinition: props.data,
            currentJobDefinitionParam: {},
            currentJobDefinitionParamType: 'STRING'
        };
        this.selectionChanged = this.selectionChanged.bind(this);
        this.getType = this.getType.bind(this);
        this.getOptions = this.getOptions.bind(this);
        this.getValue = this.getValue.bind(this);
        this.getValueField = this.getValueField.bind(this);
        this.onRowRemoving = this.onRowRemoving.bind(this);
        this.onTypeChanged = this.onTypeChanged.bind(this);
        this.paramTypes = [
            {label: 'String', type: 'STRING'},
            {label: 'Long', type: 'LONG'},
            {label: 'Double', type: 'DOUBLE'},
            {label: 'Boolean', type: 'BOOLEAN'},
            {label: 'Date', type: 'DATE'}
        ];
    }

    getValue() {
        switch (this.state.currentJobDefinitionParam.type) {
            case 'STRING':
                return this.state.currentJobDefinitionParam.stringValue;
            case 'LONG':
                return this.state.currentJobDefinitionParam.longValue;
            case 'DATE':
                return this.state.currentJobDefinitionParam.dateValue;
            case 'DOUBLE':
                return this.state.currentJobDefinitionParam.doubleValue;
            case 'BOOLEAN':
                return this.state.currentJobDefinitionParam.booleanValue;
            default:
                return '';
        }
    }

    getValueField() {
        switch (this.state.currentJobDefinitionParam.type) {
            case 'STRING':
                return 'stringValue';
            case 'LONG':
                return 'longValue';
            case 'DATE':
                return 'dateValue';
            case 'DOUBLE':
                return 'doubleValue';
            case 'BOOLEAN':
                return 'booleanValue';
            default:
                return '';
        }
    }

    getType() {
        console.log(this.state.currentJobDefinitionParam.type);
        switch (this.state.currentJobDefinitionParam.type) {
            case 'STRING':
                return 'dxTextBox';
            case 'LONG':
                return 'dxTextBox';
            case 'DATE':
                return 'dxDateBox';
            case 'DOUBLE':
                return 'dxTextBox';
            case 'BOOLEAN':
                return 'dxCheckBox';
            default:
                return '';
        }
    }

    getOptions() {
        switch (this.state.currentJobDefinitionParam.type) {
            case 'STRING':
                return {value: this.state.currentJobDefinitionParam.stringValue};
            case 'LONG':
                return {value: this.state.currentJobDefinitionParam.longValue};
            case 'DATE':
                return {value: this.state.currentJobDefinitionParam.dateValue};
            case 'DOUBLE':
                return {value: this.state.currentJobDefinitionParam.doubleValue};
            case 'BOOLEAN':
                return {value: this.state.currentJobDefinitionParam.booleanValue};
            default:
                return '';
        }
    }

    setValue(param, value) {
        if (param === undefined) {
            return null;
        }
        switch (param.type) {
            case 'STRING':
                param.stringValue = value;
                break;
            case 'LONG':
                param.longValue = value;
                break;
            case 'DATE':
                param.dateValue = value;
                break;
            case 'DOUBLE':
                param.doubleValue = value;
                break;
            case 'BOOLEAN':
                param.booleanValue = value;
                break;
            default:
                return '';
        }
    }

    onRowRemoving(data) {
        const jobDefinitionParam = data.row.data;
        if (jobDefinitionParam._links) {
            fetch(jobDefinitionParam._links.delete.href)
                .then(response => {
                    if (response.status === 200) {
                        infoMessage("Job definition parameter '" + jobDefinitionParam.keyName + "' deleted");
                    }
                })
                .catch((error) => {
                    errorMessage('Could not delete job definition parameter: ' + error.message);
                });
        }
        this.setState({currentJobDefinitionParam: {}});
    }

    selectionChanged(e) {
        this.setState({currentJobDefinitionParam: e.data});
    }

    onTypeChanged(e) {
        this.setState({currentJobDefinitionParamType: e.data});
    }

    render() {
        return (
            <React.Fragment>
                <DataGrid
                    id={'jobDefinitionParamsTable'}
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
                    onEditingStart={this.selectionChanged}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode={'form'}
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}>
                        <JobDefinitionParamForm jobDefinitionParam={this.state.currentJobDefinitionParam}/>
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
                        allowEditing={true}
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

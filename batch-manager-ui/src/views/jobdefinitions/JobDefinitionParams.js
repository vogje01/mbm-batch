import React from 'react';
import {CheckBox} from "devextreme-react";
import DataGrid, {Column, Editing, FilterRow, Lookup, Pager, Paging, Selection} from 'devextreme-react/data-grid';
import {errorMessage, infoMessage} from "../../util/MessageUtil";
import {jobDefinitionParamDataSource} from "./JobDefinitionDataSource";

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

class JobDefinitionParams extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.data,
            currentJobDefinitionParams: {}
        };
        this.onRowRemoving = this.onRowRemoving.bind(this);
        this.paramTypes = [
            {label: 'String', type: 'STRING'},
            {label: 'Long', type: 'LONG'},
            {label: 'Double', type: 'DOUBLE'},
            {label: 'Boolean', type: 'BOOLEAN'},
            {label: 'Date', type: 'DATE'}
        ];
    }

    getValue(param) {
        if (param === undefined) {
            return null;
        }
        switch (param.type) {
            case 'STRING':
                return param.stringValue;
            case 'LONG':
                return param.longValue;
            case 'DATE':
                return param.dateValue;
            case 'DOUBLE':
                return param.doubleValue;
            case 'BOOLEAN':
                return param.booleanValue;
            default:
                return '';
        }
    }

    getType(param) {
        if (param === undefined) {
            return null;
        }
        switch (param.type) {
            case 'STRING':
                return 'string';
            case 'LONG':
                return 'number';
            case 'DATE':
                return 'datetime';
            case 'DOUBLE':
                return 'number';
            case 'BOOLEAN':
                return 'boolean';
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
        this.setState({currentJobDefinitionParams: {}});
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    style={{margin: '30px 0 0 0'}}
                    id={'jobDefinitionParamsTable'}
                    dataSource={jobDefinitionParamDataSource(this.props.data)}
                    hoverStateEnabled={true}
                    onRowDblClick={this.onSelectionChanged}
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
                        mode={'cell'}
                        startEditAction={ondblclick}
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}/>
                    <Column
                        caption={'Name'}
                        dataField={'keyName'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Type'}
                        dataField={'type'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}>
                        <Lookup
                            dataSource={this.paramTypes}
                            displayExpr={'label'}
                            valueExpr={'type'}/>
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
                                hint: 'Edit parameter',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete parameter',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default JobDefinitionParams;

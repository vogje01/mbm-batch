import React from 'react';
import {Column, DataGrid, FilterRow, HeaderFilter, Pager, Paging, Selection, Sorting} from "devextreme-react/data-grid";
import UpdateTimer from "../../components/UpdateTimer";
import {jobExecutionParamDataSource} from "./JobExecutionDataSource";

class JobExecutionParams extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: this.props.data
        };
    }

    getValue(param) {
        if (param) {
            switch (param.typeCd) {
                case 'STRING':
                    return param.stringVal;
                case 'LONG':
                    return param.longVal;
                case 'DATE':
                    return param.dateVal;
                case 'DOUBLE':
                    return param.doubleVal;
                default:
                    return 'Invalid type';
            }
        }
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'jobExecutionParameterTable'}
                    dataSource={jobExecutionParamDataSource(this.props.data)}
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
                    <FilterRow visible={true} applyFilter={'auto'}/>
                    <HeaderFilter visible={true}/>
                    <Sorting mode={'multiple'}/>

                    <Column
                        caption={'Name'}
                        dataField={'keyName'}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        calculateCellValue={this.getValue}
                        caption={'Value'}
                        dataField={'value'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}/>
                    <Paging defaultPageSize={5}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20]}/>
                </DataGrid>
                <UpdateTimer/>
            </React.Fragment>
        );
    }
}

export default JobExecutionParams;

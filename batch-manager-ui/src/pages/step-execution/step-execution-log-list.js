import React from 'react';
import {Column, DataGrid, FilterRow, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {getTimestamp} from '../../utils/date-time-util'
import {StepExecutionLogDataSource} from "./step-execution-data-source";

class StepExecutionLogList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecutionInfo: this.props.stepExecution
        };
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'stepExecutionLogTable'}
                    dataSource={StepExecutionLogDataSource(this.props.stepExecution)}
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
                        caption={'Timestamp'}
                        dataField={'timestamp'}
                        calculateCellValue={getTimestamp}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        caption={'Level'}
                        dataField={'level'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}/>
                    <Column
                        caption={'Logger'}
                        dataField={'loggerName'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}>
                        <HeaderFilter allowSearch={true}/>
                    </Column>
                    <Column caption={'Job'}>
                        <Column
                            caption={'Name'}
                            dataField={'jobName'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}>
                            <HeaderFilter allowSearch={true}/>
                        </Column>
                        <Column
                            caption={'PID'}
                            dataField={'jobPid'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}/>
                        <Column
                            caption={'Version'}
                            dataField={'jobVersion'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}/>
                    </Column>
                    <Column
                        caption={'Step Name'}
                        dataField={'stepName'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}>
                        <HeaderFilter allowSearch={true}/>
                    </Column>
                    <Column caption={'Thread'}>
                        <Column
                            caption={'Name'}
                            dataField={'thread'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}/>
                        <Column
                            caption={'ID'}
                            dataField={'threadId'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}
                            width={50}/>
                        <Column
                            caption={'Prio'}
                            dataField={'threadPriority'}
                            allowSorting={true}
                            allowFiltering={true}
                            allowGrouping={false}
                            allowReordering={true}
                            width={50}/>
                    </Column>
                    <Column
                        caption={'Message'}
                        dataField={'message'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}/>
                    <RemoteOperations sorting={true} paging={true}/>
                    <Paging defaultPageSize={5}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20]} showInfo={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default StepExecutionLogList;

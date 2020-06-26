import {dateTimeCellTemplate, getRunningTime} from "../../utils/date-time-util";
import React from "react";
import {Item, Toolbar} from "devextreme-react/toolbar";
import {Column, DataGrid, FilterRow, HeaderFilter, Pager, Paging, RemoteOperations, Selection, Sorting} from "devextreme-react/data-grid";
import {StepExecutionDataSource} from "../step-execution/step-execution-data-source";

class JobExecutionDetailsSteps extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.currentJobExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <Toolbar>
                    <Item
                        location="before"
                        widget="dxButton"
                        options={{
                            icon: "material-icons-outlined ic-refresh", onClick: () => {
                                this.setState({})
                            }, hint: 'Refresh step execution list.'
                        }}/>
                </Toolbar>
                <DataGrid
                    dataSource={StepExecutionDataSource(this.props.currentJobExecution, this.state.filterName)}
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
                        caption={'Job Name'}
                        dataField={'jobName'}
                        allowSorting={true}
                        allowFiltering={true}
                        allowGrouping={false}
                        allowReordering={true}>
                        <HeaderFilter allowSearch={true}/>
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
                    <Column
                        dataField={'stepExecutionId'}
                        caption={'ID'}
                        allowSorting={true}
                        allowGrouping={false}
                        allowReordering={true}
                        width={30}/>
                    <Column
                        dataField={'status'}
                        caption={'Status'}
                        dataType={'string'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={true}
                        width={100}/>
                    <Column
                        dataField={'exitCode'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'exitMessage'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'hostName'}
                        caption={'Host name'}
                        dataType={'string'}
                        visible={false}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={true}/>
                    <Column
                        dataField={'nodeName'}
                        caption={'Node name'}
                        dataType={'string'}
                        visible={false}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={true}/>
                    <Column
                        cellTemplate={dateTimeCellTemplate}
                        dataField={'startTime'}
                        caption={'Started'}
                        dataType={'datetime'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={140}/>
                    <Column
                        cellTemplate={dateTimeCellTemplate}
                        dataField={'endTime'}
                        caption={'Ended'}
                        dataType={'datetime'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={140}/>
                    <Column
                        cellTemplate={dateTimeCellTemplate}
                        dataField={'lastUpdated'}
                        caption={'Last Update'}
                        dataType={'datetime'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={140}/>
                    <Column
                        calculateCellValue={getRunningTime}
                        dataField={'runningTime'}
                        caption={'Running'}
                        dataType={'datetime'}
                        allowEditing={true}
                        allowSorting={true}
                        allowReordering={true}
                        width={100}/>
                    <Column
                        dataField={'totalCount'}
                        caption={'Total'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={60}/>
                    <Column
                        calculateCellValue={this.getReadCount}
                        dataField={'readCount'}
                        caption={'Read'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={100}/>
                    <Column
                        calculateCellValue={this.getReadSkipCount}
                        dataField={'readSkipCount'}
                        caption={'Read Skip'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        visible={false}
                        width={100}/>
                    <Column
                        calculateCellValue={this.getWriteCount}
                        caption={'Write'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={100}/>
                    <Column
                        calculateCellValue={this.getWriteSkipCount}
                        caption={'Write Skip'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        visible={false}
                        width={100}/>
                    <Column
                        calculateCellValue={this.getFilterCount}
                        caption={'Filtered'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        width={100}/>
                    <Column
                        dataField={'commitCount'}
                        caption={'Commits'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        visible={false}
                        width={60}/>
                    <Column
                        dataField={'rollbackCount'}
                        caption={'Rollbacks'}
                        dataType={'number'}
                        allowSorting={true}
                        allowReordering={true}
                        allowFiltering={false}
                        visible={false}
                        width={60}/>
                    <Column
                        dataField={'createdBy'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'createdAt'}
                        dataType={'datetime'}
                        visible={false}/>
                    <Column
                        dataField={'modifiedBy'}
                        dataType={'string'}
                        visible={false}/>
                    <Column
                        dataField={'modifiedAt'}
                        dataType={'datetime'}
                        visible={false}/>
                    <RemoteOperations sorting={true} paging={true}/>
                    <Paging defaultPageSize={10}/>
                    <Pager showPageSizeSelector={true} allowedPageSizes={[5, 10, 20, 50, 100]}
                           showNavigationButtons={true} showInfo={true} visible={true}/>
                </DataGrid>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsSteps
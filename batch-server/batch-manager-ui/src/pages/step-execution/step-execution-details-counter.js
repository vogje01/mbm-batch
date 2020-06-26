import Form, {SimpleItem} from "devextreme-react/form";
import React from "react";
import {getPctCounter} from "../../utils/counter-util";

class StepExecutionDetailsCounter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.currentStepExecution
        };
    }

    getReadCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.readCount) : null;
    }

    getReadSkipCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.readSkipCount) : null;
    }

    getWriteCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.writeCount) : null;
    }

    getWriteSkipCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.writeSkipCount) : null;
    }

    getFilterCount(rowData) {
        return rowData ? getPctCounter(rowData.totalCount, rowData.filterCount) : null;
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentStepExecution}
                    colCount={4}>
                    <SimpleItem dataField="totalCount" readOnly={true}/>
                    <SimpleItem dataField="readCount" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.getReadCount(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="readSkipCount" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.getReadSkipCount(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="writeCount" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.getWriteCount(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="writeSkipCount" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.getWriteSkipCount(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="filtered" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.getFilterCount(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="commitCount" readOnly={true}/>
                    <SimpleItem dataField="rollbackCount" readOnly={true}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetailsCounter
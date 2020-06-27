import Form, {SimpleItem} from "devextreme-react/form";
import React from "react";

class JobExecutionLogDetailsStacktrace extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecutionLog: props.currentJobExecutionLog
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobExecutionLog}
                    colCount={1}>
                    <SimpleItem dataField="extendedStacktrace" editorType="dxTextArea" editorOptions={{height: 90, readOnly: true}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobExecutionLogDetailsStacktrace
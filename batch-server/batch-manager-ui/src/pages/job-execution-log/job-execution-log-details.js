import React from "react";
import Accordion from "devextreme-react/accordion";
import JobExecutionLogDetailsMain from "./job-execution-log-details-main";
import JobExecutionLogDetailsStacktrace from "./job-execution-log-details-stacktrace";

class JobExecutionLogDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecutionLog: props.data
        };
        this.customTitle = this.customTitle.bind(this);
        this.customItem = this.customItem.bind(this);
        this.pages = [
            {title: 'Main', item: 'Main'},
            {title: 'Stacktrace', item: 'StackTrace'}
        ];
    }

    customTitle(data) {
        return (
            <span>{data.title}</span>
        );
    }

    customItem(data) {
        switch (data.item) {
            case 'Main':
                return (<JobExecutionLogDetailsMain currentJobExecutionLog={this.state.currentJobExecutionLog.data}/>);
            case 'StackTrace':
                return (<JobExecutionLogDetailsStacktrace currentJobExecutionLog={this.state.currentJobExecutionLog.data}/>)
            default:
                return null;
        }
    }

    render() {
        return (
            <React.Fragment>
                <Accordion
                    dataSource={this.pages}
                    collapsible={true}
                    itemTitleRender={this.customTitle}
                    itemRender={this.customItem}>
                </Accordion>
            </React.Fragment>
        );
    }
}

export default JobExecutionLogDetailsPage
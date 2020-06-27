import React from "react";
import Accordion from "devextreme-react/accordion";
import JobScheduleDetailsMain from "./job-schedule-details-main";
import JobScheduleAgentList from "./job-schedule-agent-list";
import JobScheduleAgentGroupList from "./job-schedule-agentgroup-list";
import JobScheduleDetailsAuditing from "./job-schedule-details-auditing";

class JobScheduleDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: props.data
        };
        this.customTitle = this.customTitle.bind(this);
        this.customItem = this.customItem.bind(this);
        this.pages = [
            {title: 'Main', item: 'Main'},
            {title: 'Agent List', item: 'AgentList'},
            {title: 'Agent Group List', item: 'AgentGroupList'},
            {title: 'Auditing', item: 'Auditing'}
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
                return (<JobScheduleDetailsMain currentJobSchedule={this.state.currentJobSchedule.data}/>);
            case 'AgentList':
                return (<JobScheduleAgentList currentJobSchedule={this.state.currentJobSchedule.data}/>);
            case 'AgentGroupList':
                return (<JobScheduleAgentGroupList currentJobSchedule={this.state.currentJobSchedule.data}/>);
            case 'Auditing':
                return (<JobScheduleDetailsAuditing currentJobSchedule={this.state.currentJobSchedule.data}/>)
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

export default JobScheduleDetailsPage
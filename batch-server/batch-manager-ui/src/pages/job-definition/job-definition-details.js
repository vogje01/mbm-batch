import React from "react";
import Accordion from "devextreme-react/accordion";
import JobDefinitionDetailsMain from "./job-definition-details-main";
import JobDefinitionDetailsAuditing from "./job-definition-details-auditing";

class JobDefinitionDetailsPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.data
        };
        this.customTitle = this.customTitle.bind(this);
        this.customItem = this.customItem.bind(this);
        this.pages = [
            {title: 'Main', item: 'Main'},
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
                return (<JobDefinitionDetailsMain currentJobDefinition={this.state.currentJobDefinition.data}/>);
            case 'Auditing':
                return (<JobDefinitionDetailsAuditing currentJobDefinition={this.state.currentJobDefinition.data}/>)
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

export default JobDefinitionDetailsPage
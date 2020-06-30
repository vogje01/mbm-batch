import React from "react";
import Diagram from 'devextreme-react/diagram';
import {data} from './job-definition-data';

class JobDefinitionDetailsDiagram extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.data
        };
        this.diagramRef = React.createRef();
    }

    componentDidMount() {
        let diagram = this.diagramRef.current.instance;
        diagram.import(JSON.stringify(data));
        /*fetch('data/diagram-flow.json')
            .then(function(response) {
                return response.json();
            })
            .then(function(json) {
                diagram.import(JSON.stringify(data));
            });*/
    }

    render() {
        return (
            <Diagram
                style={{background: '#ffffff'}}
                id="diagram"
                ref={this.diagramRef}
                pageColor={'#ffffff'}
                readOnly={true}
                autoZoomMode="fitWidth"/>
        );
    }
}

export default JobDefinitionDetailsDiagram
import React from 'react';
import {TextArea} from "devextreme-react";

import {errorMessage, infoMessage} from "../../utils/message-util";
import Toolbar, {Item} from "devextreme-react/toolbar";

class JobDefinitionExport extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinitions: []
        };
    }

    componentDidMount() {
        fetch(process.env.REACT_APP_API_URL + 'jobdefinitions/export', {
            headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')},
            method: 'GET'
        })
            .then(response => response.json())
            .then((data) => {
                this.setState({currentJobDefinitions: data})
                infoMessage('Job definitions exported');
            })
            .catch((error) => {
                errorMessage('Job definitions export error: ' + error.message);
            });
    }

    close() {
        this.props.history.push('/jobdefinitions')
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Job Definition Export</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <Toolbar>
                            <Item
                                location="after"
                                widget="dxButton"
                                options={{text: "Close", stylingMode: "Contained", onClick: this.close.bind(this)}}/>
                        </Toolbar>
                        <TextArea
                            autoResizeEnabled={true}
                            value={JSON.stringify(this.state.currentJobDefinitions, null, 4)}/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default JobDefinitionExport;
import React from 'react';
import Tabs from "devextreme-react/tabs";
import JobDefinitionMain from "./JobDefinitionMain";
import JobDefinitionParams from "./JobDefinitionParams";
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";
import FisPage from "../../components/FisPage";

class JobDefinitionDetails extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: props.currentJobDefinition,
            selectedIndex: 0
        };
        this.onTabSelectionChanged = this.onTabSelectionChanged.bind(this);
        this.tabs = [
            {id: 0, text: 'Main', icon: 'material-icons-outlined ic-home md-18', content: 'Main'},
            {id: 1, text: 'Parameter', icon: 'material-icons-outlined ic-list md-18', content: 'Parameter content'}
        ];
    }

    onTabSelectionChanged(index) {
        if (index.name === 'selectedIndex') {
            this.setState({
                selectedIndex: index.value,
            });
        }
    }

    render() {
        return (
            <React.Fragment>
                <Popup
                    id={'jobDefinitionDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Job Definition Details: ' + (this.props.currentJobDefinition.jobName)}
                    height={'auto'}
                    minWidth={500}
                    minHeight={480}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Tabs
                        dataSource={this.tabs}
                        selectedIndex={this.state.selectedIndex}
                        onOptionChanged={this.onTabSelectionChanged}/>
                    <div>
                        <JobDefinitionMain data={this.props.currentJobDefinition}
                                           hidden={this.state.selectedIndex !== 0}
                                           closePopup={this.props.closePopup}/>
                        <JobDefinitionParams data={this.props.currentJobDefinition}
                                             hidden={this.state.selectedIndex !== 1}
                                             closePopup={this.props.closePopup}/>
                    </div>
                    <Button
                        width={90}
                        text={'Close'}
                        type={'success'}
                        stylingMode={'outlined'}
                        onClick={this.props.closePopup}
                        style={{float: 'right', margin: '5px 10px 10px 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default JobDefinitionDetails;
import React from 'react';
import Form, {Item} from "devextreme-react/form";
import {Button} from "devextreme-react";
import {updateItem} from "../../components/ServerConnection";

const screenByWidth = (width) => width < 720 ? 'sm' : 'md';

class JobDefinitionMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobDefinition: this.props.currentJobDefinition
        };
        this.saveJobDefinition = this.saveJobDefinition.bind(this);
        this.types = ['JAR', 'DOCKER'];
    }

    saveJobDefinition() {
        updateItem(this.props.data._links.update.href, this.props.data, 'jobDefinitionDto')
            .then(this.props.closePopup());
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <Form
                    id={'form'}
                    formData={this.props.data}
                    labelLocation={'top'}
                    minColWidth={200}
                    colCount={2}
                    screenByWidth={screenByWidth}>
                    <Item dataField={'id'} editorOptions={{disabled: true}}/>
                    <Item dataField={'name'}/>
                    <Item dataField={'label'}/>
                    <Item dataField={'jobGroupDto.name'}/>
                    <Item dataField={'jobVersion'}/>
                    <Item dataField={'type'} editorType={'dxSelectBox'} editorOptions={{items: this.types}}/>
                    <Item dataField={'active'} editorType={'dxCheckBox'}/>
                    <Item dataField={'command'} colSpan={2}/>
                    <Item dataField={'fileName'} colSpan={2}/>
                    <Item dataField={'workingDirectory'} colSpan={2}/>
                    <Item dataField={'description'} colSpan={2} editorType={'dxTextArea'} editorOptions={{height: 90}}/>
                </Form>

                <Button
                    width={90}
                    text={'Apply'}
                    type={'success'}
                    stylingMode={'contained'}
                    onClick={this.saveJobDefinition}
                    style={{float: 'right', margin: '5px 10px 0 0'}}/>
            </React.Fragment>
        );
    }
}

export default JobDefinitionMain;

import { Modal, Button, Row, Col, Form, Dropdown, InputGroup } from 'react-bootstrap';
import fetch from 'isomorphic-unfetch';
import { Container } from "next/app";
import ModalHeader from 'react-bootstrap/ModalHeader';


class AddVocabulary extends React.Component {
    constructor() {
        super();
        this.sendData = this.sendData.bind(this);
        this.state = {
            vocabulary: '',
            translation_language: '',
            vocabulary_translation: ''
        };
    };

    async sendData() {
        var body = this.prepareData();
        if(body == -1)
            return;
        console.log(body)
        var result = await fetch('http://localhost:8080/api/vocabulary', {
            method: 'POST',
            headers: new Headers({'content-type': 'application/json'}),
            body: body
        });
        if(result.status == 200){
            alert("OK saved!")
        }
        else{
            alert("An error occured!")
        }
    }
    prepareData(){
        if(this.state.translation_language == '' || this.state.vocabulary=='' || this.state.vocabulary_translation=='')
        {
            alert('Pleas provied the necessary input!')
            return -1;
        }
        else{
            return JSON.stringify({
                vocabulary: this.state.vocabulary,
                translations: {
                [this.state.translation_language]: this.state.vocabulary_translation
                }
            })
        }
        
    }
    handleChange_Vocabulary = (e) => {
        this.setState({ vocabulary: e.target.value });
    }

    handleChange_Translation_Language = (e) => {
        this.setState({ translation_language: e.target.value });
    }

    handleChange_Voacbulary_Translation = (e) => {
        this.setState({ vocabulary_translation: e.target.value });
    }

 
    render() {
        return (
            <main>
                <h1 style={{ marginBottom: "3%", marginTop: "10%" }}>Create new vocabulary</h1>
                <Row style={{ marginBottom: "2%" }}>
                    <Col>
                        <Dropdown>
                            <Dropdown.Toggle style={{ width: 220 }} disabled variant="primary" id="dropdown-basic">
                                Englisch
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                <Dropdown.Item style={{ width: 220 }} >Deutsch</Dropdown.Item>
                                <Dropdown.Item style={{ width: 220 }} >Englisch</Dropdown.Item>
                                <Dropdown.Item style={{ width: 220 }} >Französisch</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </Col>
                    <Col>
                        <Form.Group style={{ width: 220 }} controlId="select_language" onChange={this.handleChange_Translation_Language}>
                            <select variant="primary"  name="translation_language">
                                <option value="sel_lang">Select Language</option>
                                <option value="DE">Deutsch</option>
                                <option value="EN">Englisch</option>
                                <option value="FR">Französisch</option>
                            </select>
                        </Form.Group>
                    </Col>
                </Row>
                <Row class="formatRow">
                    <Col>  <Form.Group style={{ height: 60, width: 220 }} onChange={this.handleChange_Vocabulary} controlId="formVocabulary">
                        <Form.Control type="text" placeholder="Vocabulary" />
                    </Form.Group></Col>
                    <Col></Col>
                    <Col>  <Form.Group style={{ height: 60, width: 220 }} onChange={this.handleChange_Voacbulary_Translation} controlId="formVocabulary2">
                        <Form.Control type="text" placeholder="Vocabulary" />
                    </Form.Group></Col>
                </Row>
                <Row>
                    <Col></Col>
                    <Col><Button style={{ width: "200px" }} type="button" onClick={this.sendData}>Save new Vocabulary</Button></Col>
                    <Col></Col>
                </Row>
            </main>
        );
    }
}

export default AddVocabulary;

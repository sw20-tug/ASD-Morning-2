import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { Form, Card } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import React, { Component } from 'react';
import fetch from 'isomorphic-unfetch';
import Link from 'next/link'

// render() always renders anew after setState

class RandomTest extends React.Component {
  constructor(props) {
    super(props);
    console.log("URL Query: ", this.props.query);

    // Binds
    this.handleChange_Translation = this.handleChange_Translation.bind(this);
    this.updateTest = this.updateTest.bind(this);
    this.prepareNextVocab = this.prepareNextVocab.bind(this);
    this.prepareVocabList = this.prepareVocabList.bind(this);
    this.finishTest = this.finishTest.bind(this);
    this.calculateAccuracy = this.calculateAccuracy.bind(this);
    this.saveTest = this.saveTest.bind(this);

    this.state = {
      items:[],
      vocabulary: [],
      given_language: "",
      tested_language: "",
      test_random: false,
      given_lang_short: "",
      tested_lang_short: "",
      repetitions: 0,
      test_index: 0,
      current_vocab: "",
      current_tested_vocab: "",
      result_value: 0,
      result_message: " ",
      answer: " ", 
      accuracy: 0,
      vocabulary_display_card: "test_active_vocabulary_display",
      vocabulary_list: [], 
      repetitions_counter: 0,
      list_size: 0, 
      correct_words : 0, 
      num_tested_words : 0, 
      incorrect_words_visibility: "test_active_incorrect_words_list_active", 
      interface_visibility: "test_active_wrapper",
      continue_string: ""
    };

    this.prepareStatesFromQuery();
  }

  static async getInitialProps({query}) {
    console.log(query)
    return {query}
  }

  prepareStatesFromQuery() {
    if (this.props.query.continue) { this.buildSavedTest(); } 
    else {
      this.state.given_language = this.props.query.given_language;
      this.state.tested_language = this.props.query.tested_language;
      this.state.test_random = this.props.query.random;
      this.state.repetitions = this.props.query.repetitions;

      if (this.state.test_random == "true") { this.buildRandomTest(); }
      else { this.buildSelectedTest(); }
    }
  }

  buildSavedTest() {
    fetch("http://localhost:8080/api/testing_mode/continue")
      .then((response) => response.json())
      .then((data) => {
        this.state.items = data.items;
        this.state.vocabulary = data.vocabulary;
        this.state.given_language = data.given_language;
        this.state.tested_language = data.tested_language;
        this.state.test_random = data.test_random;
        this.state.given_lang_short = data.given_lang_short;
        this.state.tested_lang_short = data.tested_lang_short;
        this.state.repetitions = data.repetitions;
        this.state.test_index = data.test_index;
        this.state.current_vocab = data.current_vocab;
        this.state.current_tested_vocab = data.current_tested_vocab;
        this.state.result_value = data.result_value;
        this.state.result_message = data.result_message;
        this.state.answer = data.answer;
        this.state.accuracy = data.accuracy;
        this.state.vocabulary_display_card = data.vocabulary_display_card;
        this.state.vocabulary_list = data.vocabulary_list;
        this.state.repetitions_counter = data.repetitions_counter;
        this.state.list_size = data.list_size;
        this.state.correct_words = data.correct_words;
        this.state.num_tested_words = data.num_tested_words;
        this.state.incorrect_words_visibility = data.incorrect_words_visibility;
        this.state.interface_visibility = data.interface_visibility;
        this.setState({continue_string: "go!"});
      })
      .catch((error) => alert(error))
  }

  buildSelectedTest() {
    this.setLanguages();
    let a = JSON.parse(this.props.query.vocabs)
    console.log("json", a.vocabs);
    this.state.vocabulary = a.vocabs;
    this.state.list_size = a.vocabs.length;
    this.state.current_vocab = this.getCurrentVocab();
    this.state.current_tested_vocab = this.getCurrentTestedVocab();
    console.log("value in selected test", a.vocabs, this.state.current_vocab, this.state.vocabulary);
    this.prepareVocabList();
  }

  buildRandomTest() {
    this.setLanguages();

    fetch('http://localhost:8080/api/vocabulary/random')
      .then((response) => response.json())
      .then((data) => { this.state.vocabulary = data;
        this.state.list_size = data.length;
      console.log("data", data); })
      .then(() => { 
        this.setState({
          current_vocab: this.getCurrentVocab(), 
          current_tested_vocab: this.getCurrentTestedVocab() })
      })
      .then(() => {
          this.prepareVocabList();
      })
      .catch((error) => {
        alert("Oops, I messed up something. Database does not respond or gives me weird reponses oof. ", error);
      });
  }

  prepareVocabList() {
    console.log("in prepareList: ", this.state.vocabulary);
    if (this.state.given_language == "German")
    {
      //this.state.vocabulary.forEach(element => this.state.vocabulary_list.push([element.translations.DE, 0]))
      this.state.vocabulary.forEach(
        vocab => this.state.vocabulary_list.push(
          {"vocabulary": vocab.translations.DE, "wrong": 0}
        )
      );
    }
    else if (this.state.given_language == "English")
    {
      //this.state.vocabulary.forEach(element => this.state.vocabulary_list.push([element.translations.EN, 0]))
      this.state.vocabulary.forEach(
        vocab => this.state.vocabulary_list.push(
          {"vocabulary": vocab.translations.EN, "wrong": 0}
        )
      );
    }
    else {
      //this.state.vocabulary.forEach(element => this.state.vocabulary_list.push([element.translations.FR, 0]))
      this.state.vocabulary.forEach(
        vocab => this.state.vocabulary_list.push(
          {"vocabulary": vocab.translations.FR, "wrong": 0}
        )
      );
    }
    console.log("after prepareList: ", this.state.vocabulary_list);
  }

  getCurrentVocab() {
    if (this.state.given_language == "German") {
      console.log("currvocab:", this.state.vocabulary[this.state.test_index].translations.DE);
      return this.state.vocabulary[this.state.test_index].translations.DE
      

    } else if (this.state.given_language == "English") {
      return this.state.vocabulary[this.state.test_index].translations.EN
      
    } else {
      return this.state.vocabulary[this.state.test_index].translations.FR
      
    }
  }

  getCurrentTestedVocab() {
    if (this.state.tested_language == "German") {
      return this.state.vocabulary[this.state.test_index].translations.DE

    } else if (this.state.tested_language == "English") {
      return this.state.vocabulary[this.state.test_index].translations.EN

    } else {
      return this.state.vocabulary[this.state.test_index].translations.FR

    }
  }

  setLanguages() {
    if (this.state.given_language == "German") {
      this.state.given_lang_short = "DE";
    } else if (this.state.given_language == "English") {
      this.state.given_lang_short = "EN";
    } else {
      this.state.given_lang_short = "FR";
    }
    if (this.state.tested_language == "German") {
      this.state.test_lang_short = "DE";
    } else if (this.state.tested_language == "English") {
      this.state.test_lang_short = "EN";
    } else {
      this.state.test_lang_short = "FR";
    }
  }

  updateTest() {
    console.log("Update function called.");
    let answerString = this.state.answer;
    let testVocabString = this.state.current_tested_vocab;
    this.state.num_tested_words++;
    if (answerString === testVocabString)
    {
        this.state.correct_words++;
        this.calculateAccuracy();
        this.state.result_message = "Correct!";
        this.setState({
            vocabulary_display_card: "test_active_result_message_correct",
            result_value: 1
        })
        setTimeout(this.prepareNextVocab, 1000);
    }

    else
    {
        this.calculateAccuracy();
        //this.state.vocabulary_list.get(this.state.current_vocab).value++;
        this.state.vocabulary_list.forEach(e => { if (e.vocabulary == this.state.current_vocab) { e.wrong++; } });
        
        this.state.result_message = "Wrong! Translation: " + this.state.current_tested_vocab;
        this.setState({
            vocabulary_display_card: "test_active_result_message_incorrect",
            result_value: 2
        })
        setTimeout(this.prepareNextVocab, 2000);
    }

    document.getElementById("vocab-input").value="";

  }

  prepareNextVocab() {
      console.log("After Wait in prepareNextVocab!", this.state.repetitions_counter);
      this.state.result_message = "";

      this.state.test_index++;
      if (this.state.test_index == this.state.list_size)
      {
          this.state.repetitions_counter++;
          if (this.state.repetitions_counter == this.state.repetitions)
          {
              this.finishTest();
          }
          this.state.test_index = 0;
      }
      this.state.current_vocab = this.getCurrentVocab();
      this.state.current_tested_vocab = this.getCurrentTestedVocab();
      let cnt = 0;

      
      //while(this.state.vocabulary_list.get(this.state.current_vocab).value < this.state.repetitions_counter && cnt < this.state.list_size + 1)
      let current_vocab_element = this.state.vocabulary_list.filter(e => e.vocabulary == this.state.current_vocab);
      let current_wrong = current_vocab_element.wrong;
      
      while(current_wrong < this.state.repetitions_counter && cnt < this.state.list_size + 1)
      {
        this.state.test_index++;
        if (this.state.test_index == this.state.list_size)
        {
            this.state.repetitions_counter++;
          if (this.state.repetitions_counter == this.state.repetitions)
          {
              this.finishTest();
          }
            this.state.test_index = 0;
        }
        this.state.current_vocab = this.getCurrentVocab();
        this.state.current_tested_vocab = this.getCurrentTestedVocab();
        cnt++;
      }

      if(cnt == this.state.list_size + 1)
      {
        this.finishTest();
      }
      this.state.vocabulary_display_card = "test_active_vocabulary_display";
        
      
      this.state.answer = " ";
      this.setState({
          result_value: 0
      })
  }

  finishTest() {
      console.log("finish Test!!!");
      this.setState({
          interface_visibility: "test_active_wrapper_finish",
          incorrect_words_visibility : "test_active_incorrect_words_list_finish"
      })
  }

  calculateAccuracy() {
      console.log("numbers: ", this.state.correct_words, this.state.num_tested_words);
      this.state.accuracy = ((this.state.correct_words * 100) / this.state.num_tested_words);
      console.log("in accuracy: ", this.state.accuracy);
  }

  renderLog() {
    console.log("Render method fired!");
  }

  handleChange_Translation({ target }) {
      this.state.answer = target.value;
      console.log("answer: ", this.state.answer);
  }

  createTable() {
    let table = []

    //this.state.vocabulary_list[Symbol.iterator] = function* () {
    //  yield* [...this.entries()].sort((a, b) => a[1] - b[1]);
    //}
    this.state.vocabulary_list.sort(function(a, b) {
      let comparison = 0;
      if (a.wrong < b.wrong) { comparison = 1; }
      else if (a.wrong > b.wrong) { comparison = -1; }
      return comparison;
    });

    // Outer loop to create parent
    this.state.vocabulary_list.forEach(e => {
      let children = []
      children.push(<td>{`${e.vocabulary}`}</td>)
      children.push(<td>{`${e.wrong}`}</td>)
    
      //Create the parent and add the children
      table.push(<tr>{children}</tr>)
    })
    return table
    
  }

  saveTest() {
    let payload = JSON.stringify(this.state);
    
    fetch("http://localhost:8080/api/testing_mode/save", {
      method: "POST",
      headers: new Headers({ 
        'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json;charset=UTF-8' 
        }),
      body: payload
    })
    .then((response) => {
      if (response.status == 200) {
        alert("You've successfully saved the test. Please don't use it to cheat, ok?");
      }
      else {
        alert("Something went horribly wrong...");
      }
    });
  }
  
  render() {
    this.renderLog();
    return(
      <main className="test_main">
        <div className="test_active_headline">
            Test your knowledge!
        </div>
        <div className={this.state.interface_visibility}>
          <div>
              <Card className={ this.state.vocabulary_display_card }>
              <Card.Body>{ this.state.current_vocab }</Card.Body>
              </Card>
          </div>

          <div className={this.state.vocabulary_display_card}>
              { this.state.result_message }
          </div>
          
          <Form.Group className="test_active_input">
              <InputGroup size="sm" className="mb-3" >
              <InputGroup.Prepend >
                  <InputGroup.Text className="inputGroup-sizing-sm" >
                      { this.state.test_lang_short }
                  </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl 
                  id="vocab-input"
                  type="text" 
                  name={ this.state.tested_lang_short } 
                  onChange={this.handleChange_Translation} 
                  aria-label="Small" 
                  aria-describedby="inputGroup-sizing-sm" />
              </InputGroup>
          </Form.Group>
          <div className="test_active_submit_wrapper">
              <Button variant="outline-primary" onClick={this.updateTest}> Check </Button>
              <Button variant="outline-primary" className="test_active_save_button" onClick={ this.saveTest }>Save Test</Button>
          </div>

          <div className="test_active_accuracy">
              Accuracy: { this.state.accuracy }%
          </div>
        </div>
        
        <div className={this.state.incorrect_words_visibility}>
          <table className="table">
            <thead>
              <tr>
                  <th scope="col">Vocabulary</th>
                  <th scope="col">Wrong</th>
              </tr>
            </thead>
            <tbody>
              {this.createTable()}  
            </tbody>
          </table>

          <div className="test_active_submit_wrapper">
            <Link href="/testing_mode">
              <Button variant="outline-primary"> Finish </Button>
            </Link>
          </div>
        </div>
            
      </main>
    );
  }
}

export default RandomTest;
/*

Test-Size <- Vocab_list size
Test_count ++ pro Wort
Test_count == test-size -> 1. Durchlauf vorbei

2. Durchlauf:
Skip Wort wenn richtig beim 1. Mal und ++ test_count
Test endet if test_count == (test_size * repetitions)

| Wort      | value
|
| Haus      | 0
| Tier      | 1
| Tastatur  | 0
| .         | 0
| .         | 1
| .         | 0


Check ob 1:

Tier
.
                          //</table>this.state.items.push(<td key={key}>{key}</td>)
                          //</div>this.state.items.push(<td key={value}>{map.get(key).value}</td>
*/
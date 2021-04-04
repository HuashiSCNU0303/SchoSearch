import { reactive } from "vue"

const papers = reactive({

})

const paperMethods = {
  addPaper (author_1, author_2, newPapers) {
    var key = author_1 + "_" + author_2;
    papers[key] = newPapers;
  },
  findPaper (author_1, author_2) {
    var key = author_1 + "_" + author_2;
    if (Object.prototype.hasOwnProperty.call(papers, key)) {
      return papers[key];
    }
    return [];
  }
}

export default {
  papers,
  paperMethods,
}
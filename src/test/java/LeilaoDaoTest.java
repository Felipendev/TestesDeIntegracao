import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JPAUtil;
import util.builder.LeilaoBuilder;
import util.builder.UsuarioBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;


class LeilaoDaoTest {

    private EntityManager em;
    private LeilaoDao dao;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarUmLeilao() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("12345678")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Teclado")
                .comValorInicial("100")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);
        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertNotNull(salvo);
    }


    @Test
    void deveriaAtualizarUmLeilao() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("12345678")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Teclado")
                .comValorInicial("100")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);
        leilao.setNome("Teclado");
        leilao.setValorInicial(new BigDecimal(400));
        leilao = dao.salvar(leilao);
        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertEquals("Teclado", salvo.getNome());
        Assert.assertEquals(new BigDecimal("400"), salvo.getValorInicial());
    }

}
